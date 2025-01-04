package com.loanapp.repository;

import com.loanapp.model.LoanProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author :  Tiamiyu Kehinde
 * @project : Loan-App
 * @date :  1/3/25
 * @email :TiamiyuKehinde5@gmail.com
 */
public interface LoanProductRepository extends JpaRepository<LoanProduct, Long> {
    Optional<LoanProduct> findByIdAndStatus(Long id, LoanProduct.ProductStatus status);
}
