package com.loanapp.service.impl;

import com.loanapp.dto.LoanDto;
import com.loanapp.dto.TransactionDto;
import com.loanapp.dto.UserDto;
import com.loanapp.model.Transaction;
import com.loanapp.repository.TransactionRepository;
import com.loanapp.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author :  Tiamiyu Kehinde
 * @project : Loan-App
 * @date :  1/3/25
 * @email :TiamiyuKehinde5@gmail.com
 */

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;


    @Override
    public List<TransactionDto> getUserTransactions(Long userId) {
        List<Transaction> transactions = transactionRepository
                .findByUserId(userId);

        return transactions.stream()
                .map(transaction -> TransactionDto
                        .builder()
                        .id(transaction.getId())
                        .amount(transaction.getAmount())
                        .type(transaction.getType())
                        .createdAt(transaction.getCreatedDate())
                        .updatedAt(transaction.getModifiedDate())
                        .loan(LoanDto.builder()
                                .id(transaction.getLoan().getId())
                                .product(transaction.getLoan().getProduct())
                                .amount(transaction.getLoan().getAmount())
                                .interestRate(transaction.getLoan().getInterestRate())
                                .status(transaction.getLoan().getStatus())
                                .approvalDate(transaction.getLoan().getApprovalDate())
                                .repaymentDate(transaction.getLoan().getRepaymentDate())
                                .build())
                        .user(UserDto.builder()
                                .id(transaction.getUser().getId())
                                .email(transaction.getUser().getEmail())
                                .firstName(transaction.getUser().getFirstName())
                                .lastName(transaction.getUser().getLastName())
                                .build())
                        .build())
                .toList();

    }
}
