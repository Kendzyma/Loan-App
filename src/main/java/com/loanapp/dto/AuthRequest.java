package com.loanapp.dto;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author :  Tiamiyu Kehinde
 * @project : Loan-App
 * @date :  1/2/25
 * @email : Tiamiyu@getrova.com, TiamiyuKehinde5@gmail.com
 */


public record AuthRequest(
    @NotNull(message = "Username is required")
     String email,
    @NotNull(message = "Password is required")
     String password){
}
