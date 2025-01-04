package com.loanapp.service.impl;

import com.loanapp.dto.TransactionDto;
import com.loanapp.enums.RoleName;
import com.loanapp.model.*;
import com.loanapp.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class TransactionServiceImplTest {

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    private User user;
    private Loan loan;
    private Transaction transaction;

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
                .roles(Set.of(Role.builder().id(1L).name(RoleName.USER).build()))
                .build();

        loan = Loan.builder()
                .id(1L)
                .user(user)
                .product(LoanProduct.builder().id(1L).name("Standard").build())
                .amount(1000)
                .interestRate(5.0)
                .status(Loan.LoanStatus.PENDING)
                .approvalDate(LocalDateTime.now())
                .repaymentDate(LocalDateTime.now().plusMonths(12))
                .build();

        transaction = Transaction.builder()
                .id(1L)
                .amount(1000)
                .type(Transaction.TransactionType.DISBURSEMENT)
                .user(user)
                .loan(loan)
                .build();
    }

    @Test
    void getUserTransactions_ReturnsTransactionDtoList() {
        when(transactionRepository.findByUserId(anyLong())).thenReturn(List.of(transaction));

        List<TransactionDto> transactionDtos = transactionService.getUserTransactions(user.getId());

        assertNotNull(transactionDtos);
        assertEquals(1, transactionDtos.size());
        assertEquals(transaction.getId(), transactionDtos.get(0).getId());
        assertEquals(transaction.getAmount(), transactionDtos.get(0).getAmount());
        assertEquals(transaction.getType(), transactionDtos.get(0).getType());

        verify(transactionRepository, times(1)).findByUserId(user.getId());
    }
}