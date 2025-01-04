package com.loanapp.controller;

import com.loanapp.dto.TransactionDto;
import com.loanapp.model.Transaction;
import com.loanapp.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author :  Tiamiyu Kehinde
 * @project : Loan-App
 * @date :  1/3/25
 * @email :TiamiyuKehinde5@gmail.com
 */

@RequiredArgsConstructor
@RestController("/api/v1/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    @Operation(
            summary = "Get all transactions",
            description = "Get all transactions")
    @GetMapping("user/{userId}/statement")
    public ResponseEntity<List<TransactionDto>> getAllTransactions(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(transactionService.getUserTransactions(userId));
    }
}
