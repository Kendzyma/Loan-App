package com.loanapp.service;

import com.loanapp.dto.LoanDto;
import com.loanapp.dto.LoanRequest;
import com.loanapp.dto.RepaymentRequest;
import com.loanapp.model.Loan;

/**
 * @author :  Tiamiyu Kehinde
 * @project : Loan-App
 * @date :  1/3/25
 * @email :TiamiyuKehinde5@gmail.com
 */
public interface LoanService {
    LoanDto apply(LoanRequest loanRequest);

    LoanDto updateLoanStatus(Long id, Loan.LoanStatus status);

    LoanDto getLoan(Long id);

    LoanDto repayLoan(Long id, RepaymentRequest repaymentRequest);
}
