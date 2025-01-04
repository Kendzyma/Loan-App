package com.loanapp.repository;

import com.loanapp.model.Loan;
import com.loanapp.model.LoanProduct;
import com.loanapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author :  Tiamiyu Kehinde
 * @project : Loan-App
 * @date :  1/3/25
 * @email :TiamiyuKehinde5@gmail.com
 */
public interface LoanRepository extends JpaRepository<Loan, Long> {
    Optional<Loan> findByUserAndProductAndStatusIn(User user, LoanProduct product, List<Loan.LoanStatus> status);
}
