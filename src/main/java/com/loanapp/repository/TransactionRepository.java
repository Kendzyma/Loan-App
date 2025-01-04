package com.loanapp.repository;

import com.loanapp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author :  Tiamiyu Kehinde
 * @project : Loan-App
 * @date :  1/3/25
 * @email : Tiamiyu@getrova.com, TiamiyuKehinde5@gmail.com
 */
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    List<Transaction> findByUserId(Long userId);
}
