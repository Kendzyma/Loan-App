package com.loanapp.dto;

import com.loanapp.annotation.ValidPassword;
import com.loanapp.enums.RoleName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;
import java.util.Set;

/**
 * @author :  Tiamiyu Kehinde
 * @project : Loan-App
 * @date :  1/3/25
 * @email :TiamiyuKehinde5@gmail.com
 */
public record UserRequest(
        @NotNull(message = "first name is required")
        String firstName,
        @NotNull(message = "last name is required")
        String lastName,
        @NotNull(message = "email is required")
        @Email(message = "invalid email format")
        String email,
        @NotNull(message = "phone number is required")
        @Pattern(
                regexp = "^\\+?[0-9]{11}$",
                message = "invalid phone number format, must be exactly 11 digits"
        )
        String phoneNumber,
        @NotNull(message = "password is required")
        @ValidPassword(message = "Password must contain letters, a number, and a special symbol")
        String password,
        @NotEmpty(message = "roles is required")
        Set<RoleName> roles
) {
}
