package com.loanapp.service;

import com.loanapp.dto.TransactionDto;
import com.loanapp.model.Transaction;

import java.util.List;

/**
 * @author :  Tiamiyu Kehinde
 * @project : Loan-App
 * @date :  1/3/25
 * @email :TiamiyuKehinde5@gmail.com
 */
public interface TransactionService {
    List<TransactionDto> getUserTransactions(Long userId);
}
