package com.loanapp.service;

import com.loanapp.dto.AuthRequest;
import com.loanapp.dto.AuthResponse;

/**
 * @author :  Tiamiyu Kehinde
 * @project : Loan-App
 * @date :  1/3/25
 * @email : Tiamiyu@getrova.com, TiamiyuKehinde5@gmail.com
 */
public interface AuthService {
    AuthResponse login(AuthRequest request);
}
