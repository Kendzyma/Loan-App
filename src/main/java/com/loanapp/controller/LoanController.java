package com.loanapp.controller;

import com.loanapp.dto.LoanDto;
import com.loanapp.dto.LoanRequest;
import com.loanapp.dto.RepaymentRequest;
import com.loanapp.model.Loan;
import com.loanapp.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author :  Tiamiyu Kehinde
 * @project : Loan-App
 * @date :  1/3/25
 * @email : TiamiyuKehinde5@gmail.com
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/loan")
public class LoanController {
    private final LoanService loanService;

    @Operation(
            summary = "Apply for a loan",
            description = "Apply for a loan")
    @PostMapping("/apply")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<LoanDto> apply(@RequestBody @Valid LoanRequest loanRequest){
        return ResponseEntity.ok(loanService.apply(loanRequest));
    }


    @Operation(
            summary = "Update Loan Status",
            description = "Update Loan Status")
    @PutMapping("/{id}/status")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<LoanDto> updateLoanStatus(@PathVariable("id") Long id,
                                                    @RequestParam Loan.LoanStatus status){
        return ResponseEntity.ok(loanService.updateLoanStatus(id,status));
    }


    @Operation(
            summary = "Get Loan",
            description = "Get Loan")
    @GetMapping("/{id}")
    public ResponseEntity<LoanDto> getLoan(@PathVariable("id") Long id){
        return ResponseEntity.ok(loanService.getLoan(id));
    }


    @Operation(
            summary = "Repay Loan",
            description = "Repay Loan")
    @PostMapping("{id}/repay")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<LoanDto> repayLoan( @PathVariable("id") Long id,
                                             @Valid @RequestBody RepaymentRequest repaymentRequest){
        return ResponseEntity.ok(loanService.repayLoan(id,repaymentRequest));
    }
}
