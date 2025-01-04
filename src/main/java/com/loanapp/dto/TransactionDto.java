package com.loanapp.dto;

import com.loanapp.model.Loan;
import com.loanapp.model.Transaction;
import com.loanapp.model.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author :  Tiamiyu Kehinde
 * @project : Loan-App
 * @date :  1/3/25
 * @email : Tiamiyu@getrova.com, TiamiyuKehinde5@gmail.com
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    private Long id;
    private LoanDto loan;
    private UserDto user;
    private double amount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Transaction.TransactionType type;
}
