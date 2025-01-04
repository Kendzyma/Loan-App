package com.loanapp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

/**
 * @author :  Tiamiyu Kehinde
 * @project : Loan-App
 * @date :  1/3/25
 * @email :TiamiyuKehinde5@gmail.com
 */
@Builder
public record RepaymentRequest(
        @NotNull(message = "Repayment amount is required")
        @Positive(message = "Repayment amount must be positive")
        Double amount) {
}
