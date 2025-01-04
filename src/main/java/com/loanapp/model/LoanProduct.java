package com.loanapp.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author :  Tiamiyu Kehinde
 * @project : Loan-App
 * @date :  1/3/25
 * @email :TiamiyuKehinde5@gmail.com
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "loan_products")
@Entity
public class LoanProduct extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "interest_rate")
    private double interestRate;
    @Column(name = "tenure_months")
    private int tenureMonths;
    @Enumerated(EnumType.STRING)
    private ProductStatus status;


    public enum ProductStatus {
        ACTIVE,
        INACTIVE
    }
}
