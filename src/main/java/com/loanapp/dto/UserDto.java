package com.loanapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class UserDto {
    private Long id;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
}
