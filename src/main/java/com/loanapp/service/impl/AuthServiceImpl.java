package com.loanapp.service.impl;

import com.loanapp.dto.AuthRequest;
import com.loanapp.dto.AuthResponse;
import com.loanapp.exception.AuthenticationException;
import com.loanapp.security.JwtService;
import com.loanapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * @author :  Tiamiyu Kehinde
 * @project : Loan-App
 * @date :  1/3/25
 * @email :TiamiyuKehinde5@gmail.com
 */

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Override
    public AuthResponse login(AuthRequest request) {
        Authentication authentication = null;
        try {
             authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );
        }catch (Exception e){
            throw new AuthenticationException("wrong email or password");
        }

        if (authentication.isAuthenticated()) {
            String token = jwtService
                    .generateToken(request.email());
            return new AuthResponse(token);
        } else {
            throw new AuthenticationException("wrong email or password");
        }
    }
}
