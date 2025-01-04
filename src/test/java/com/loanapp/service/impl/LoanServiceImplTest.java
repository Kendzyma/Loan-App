package com.loanapp.service.impl;

import com.loanapp.dto.LoanDto;
import com.loanapp.dto.LoanRequest;
import com.loanapp.dto.RepaymentRequest;
import com.loanapp.dto.UserDto;
import com.loanapp.enums.RoleName;
import com.loanapp.exception.BadRequestException;
import com.loanapp.model.*;
import com.loanapp.repository.LoanProductRepository;
import com.loanapp.repository.LoanRepository;
import com.loanapp.repository.TransactionRepository;
import com.loanapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * @author :  Tiamiyu Kehinde
 * @project : Loan-App
 * @date :  1/4/25
 * @email : Tiamiyu@getrova.com, TiamiyuKehinde5@gmail.com
 */
class LoanServiceImplTest {
    @InjectMocks
    private LoanServiceImpl loanService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LoanProductRepository loanProductService;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private ModelMapper mapper;

    private User user;
    private LoanProduct loanProduct;
    private Loan loan;
    private LoanDto loanDto;
    private LoanRequest loanRequest;
    private RepaymentRequest repaymentRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password")
                .phoneNumber("1234567890")
                .roles(Set.of(Role.builder().name(RoleName.USER).build()))
                .build();

        loanProduct = LoanProduct.builder()
                .id(1L)
                .name("Standard")
                .status(LoanProduct.ProductStatus.ACTIVE)
                .interestRate(5.0)
                .tenureMonths(12)
                .build();

        loan = Loan.builder()
                .id(1L)
                .user(user)
                .product(loanProduct)
                .amount(1000)
                .status(Loan.LoanStatus.PENDING)
                .build();

        loanRequest = LoanRequest.builder()
                .userId(1L)
                .loanProductId(1L)
                .amount(1000.0)
                .build();

        repaymentRequest = RepaymentRequest.builder()
                .amount(500.0)
                .build();

        loanDto = LoanDto.builder()
                .id(loan.getId())
                .user(new UserDto())
                .product(new LoanProduct())
                .amount(loan.getAmount())
                .interestRate(loan.getInterestRate())
                .status(loan.getStatus())
                .build();

        when(mapper.map(any(Loan.class), eq(LoanDto.class))).thenReturn(loanDto);
    }

    @Test
    void applyLoan_Success() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(loanProductService.findByIdAndStatus(anyLong(), any())).thenReturn(Optional.of(loanProduct));
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        LoanDto result = loanService.apply(loanRequest);

        assertNotNull(result);
        assertEquals(loanDto, result);
        verify(loanRepository, times(1)).save(any(Loan.class));
    }

    @Test
    void applyLoan_UserNotFound_ThrowsException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> loanService.apply(loanRequest));
    }

    @Test
    void updateLoanStatus_ApproveLoan_Success() {
        loan.setStatus(Loan.LoanStatus.PENDING);
        when(loanRepository.findById(anyLong())).thenReturn(Optional.of(loan));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        LoanDto result = loanService.updateLoanStatus(1L, Loan.LoanStatus.APPROVED);

        assertNotNull(result);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void repayLoan_Success() {
        loan.setOutstandingBalance(1000);
        when(loanRepository.findById(anyLong())).thenReturn(Optional.of(loan));
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        LoanDto result = loanService.repayLoan(1L, repaymentRequest);
        assertNotNull(result);
        assertEquals(500, loan.getOutstandingBalance());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void getLoan_Success() {
        when(loanRepository.findById(anyLong())).thenReturn(Optional.of(loan));

        LoanDto result = loanService.getLoan(1L);

        assertNotNull(result);
        verify(loanRepository, times(1)).findById(1L);
    }
}