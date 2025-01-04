package com.loanapp.dto;

import com.loanapp.model.Loan;
import com.loanapp.model.LoanProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author :  Tiamiyu Kehinde
 * @project : Loan-App
 * @date :  1/3/25
 * @email :TiamiyuKehinde5@gmail.com
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanDto {
    private Long id;
    private UserDto user;
    private LoanProduct product;
    private double amount;
    private double interestRate;
    private Loan.LoanStatus status;
    private LocalDateTime approvalDate;
    private LocalDateTime repaymentDate;
}
