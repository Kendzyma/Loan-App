package com.loanapp.controller;

import com.loanapp.dto.ApiResponse;
import com.loanapp.dto.DefaultResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :  Tiamiyu Kehinde
 * @project : Loan-App
 * @date :  1/4/25
 * @email :TiamiyuKehinde5@gmail.com
 */

@RestController
@RequestMapping("/api/v1/health")
public class HealthController {

    @Operation(
            summary = "Health Check",
            description = "Health Check")
    @GetMapping()
    public ApiResponse<String> healthCheck(){
        return ApiResponse.ok("Good");
    }
}
