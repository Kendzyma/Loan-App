package com.loanapp.service.impl;

import com.loanapp.dto.LoanDto;
import com.loanapp.dto.LoanRequest;
import com.loanapp.dto.RepaymentRequest;
import com.loanapp.dto.UserDto;
import com.loanapp.exception.BadRequestException;
import com.loanapp.exception.ForbiddenException;
import com.loanapp.model.Loan;
import com.loanapp.model.LoanProduct;
import com.loanapp.model.Transaction;
import com.loanapp.model.User;
import com.loanapp.repository.LoanProductRepository;
import com.loanapp.repository.LoanRepository;
import com.loanapp.repository.TransactionRepository;
import com.loanapp.repository.UserRepository;
import com.loanapp.service.LoanService;
import com.loanapp.utils.LoanCalculator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author :  Tiamiyu Kehinde
 * @project : Loan-App
 * @date :  1/3/25
 * @email :TiamiyuKehinde5@gmail.com
 */

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {
    private final UserRepository userRepository;
    private final LoanProductRepository loanProductService;
    private final LoanRepository loanRepository;
    private final TransactionRepository transactionRepository;
    private final ModelMapper mapper;

    @Override
    public LoanDto apply(LoanRequest loanRequest) {
        User user = userRepository.findById(loanRequest.userId())
                .orElseThrow(() -> new BadRequestException("User not found"));

        LoanProduct loanProduct = loanProductService
                .findByIdAndStatus(loanRequest.loanProductId(), LoanProduct.ProductStatus.ACTIVE)
                .orElseThrow(() -> new BadRequestException("Loan product not found or is inactive"));

        //check existing loan by same user
        loanRepository.findByUserAndProductAndStatusIn(user, loanProduct,
                        List.of(Loan.LoanStatus.APPROVED, Loan.LoanStatus.PENDING))
                .ifPresent(loan->{
                    throw new ForbiddenException("user has an active loan for this product");
                });

        Loan loan = Loan.builder()
                .user(user)
                .product(loanProduct)
                .amount(loanRequest.amount())
                .status(Loan.LoanStatus.PENDING)
                .interestRate(LoanCalculator
                        .calculateInterestRate(loanRequest.amount(), loanProduct.getTenureMonths(), loanProduct.getInterestRate()))

                .build();

        Loan savedLoan = loanRepository.save(loan);

        return mapper.map(savedLoan, LoanDto.class);
    }

    @Override
    @Transactional
    public LoanDto updateLoanStatus(Long id, Loan.LoanStatus status) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Loan not found"));

        if (status == loan.getStatus()) {
            throw new BadRequestException("Loan status is already " + status);
        }

        User user = userRepository
                .findById(loan.getUser().getId())
                .orElseThrow(() -> new BadRequestException("User not found"));

        switch (status) {
            case APPROVED:
                loan.setApprovalDate(LocalDateTime.now());
                loan.setRepaymentDate(resolveMaturityDate(loan));
                loan.setStatus(status);
                loan.setOutstandingBalance(LoanCalculator.calculateSimpleInterestAmount(
                        loan.getAmount(),
                        loan.getInterestRate()
                        ,loan.getProduct().getTenureMonths())
                );
                loanRepository.save(loan);
                Transaction transaction = Transaction.builder()
                        .amount(loan.getAmount())
                        .type(Transaction.TransactionType.DISBURSEMENT)
                        .user(user)
                        .loan(loan)
                        .build();
                transactionRepository.save(transaction);
                break;
            case REPAID:
                loan.setRepaymentDate(LocalDateTime.now());
                loan.setStatus(status);
                loan.setOutstandingBalance(0.0);
                loanRepository.save(loan);

                Transaction trx = Transaction.builder()
                        .amount(loan.getAmount())
                        .type(Transaction.TransactionType.DISBURSEMENT)
                        .user(user)
                        .loan(loan)
                        .build();

                transactionRepository.save(trx);
                break;
            default:
                loan.setStatus(status);
                loanRepository.save(loan);
        }



        return mapper.map(loan, LoanDto.class);
    }

    @Override
    public LoanDto getLoan(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Loan not found"));

        return LoanDto.builder()
                .id(loan.getId())
                .user(UserDto.builder()
                        .id(loan.getUser().getId())
                        .email(loan.getUser().getEmail())
                        .firstName(loan.getUser().getFirstName())
                        .lastName(loan.getUser().getLastName())
                        .build())
                .product(loan.getProduct())
                .amount(loan.getAmount())
                .interestRate(loan.getInterestRate())
                .status(loan.getStatus())
                .approvalDate(loan.getApprovalDate())
                .repaymentDate(loan.getRepaymentDate())
                .build();
    }

    @Override
    @Transactional
    public LoanDto repayLoan(Long id, RepaymentRequest repaymentRequest) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Loan not found"));

        validateRepayment(loan, repaymentRequest);
        loan.setOutstandingBalance(LoanCalculator.round(loan.getOutstandingBalance(),2) - repaymentRequest.amount());
        if (loan.getOutstandingBalance() <= 0) {
            loan.setStatus(Loan.LoanStatus.REPAID);
        }

        Loan savedLoan = loanRepository.save(loan);

        User user = userRepository
                .findById(loan.getUser().getId())
                .orElseThrow(() -> new BadRequestException("User not found"));


        Transaction transaction = Transaction.builder()
                .amount(repaymentRequest.amount())
                .type(Transaction.TransactionType.REPAYMENT)
                .user(user)
                .loan(loan)
                .build();

        transactionRepository.save(transaction);
        LoanDto map = mapper.map(savedLoan, LoanDto.class);
        return map;
    }

    private void validateRepayment(Loan loan, RepaymentRequest repaymentRequest) {
        if (loan.getStatus() == Loan.LoanStatus.REPAID) {
            throw new ForbiddenException("loan is already repaid");
        }
        if (loan.getStatus() != Loan.LoanStatus.APPROVED) {
            throw new BadRequestException("Loan has not been approved");
        }
        if (repaymentRequest.amount() > loan.getOutstandingBalance()) {
            throw new BadRequestException("repayment amount is greater than outstanding balance");
        }
    }

    private LocalDateTime resolveMaturityDate(Loan loan) {
        int tenure = loan.getProduct().getTenureMonths();
        return loan.getApprovalDate().plusMonths(tenure);
    }
}
