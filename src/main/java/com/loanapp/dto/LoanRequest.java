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
public record LoanRequest(
        @NotNull(message = "user id is required")
        Long userId,
        @NotNull(message = "loan product id is required")
        Long loanProductId,
        @NotNull(message = "amount is required")
        @Positive(message = "amount must be positive")
        Double amount
) {
}
