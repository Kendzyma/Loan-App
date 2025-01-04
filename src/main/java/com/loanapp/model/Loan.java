package com.loanapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @author :  Tiamiyu Kehinde
 * @project : Loan-App
 * @date :  1/3/25
 * @email : Tiamiyu@getrova.com, TiamiyuKehinde5@gmail.com
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "loans")
@Entity
public class Loan extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private LoanProduct product;
    private double amount;
    @Column(name = "outstanding_balance")
    private double outstandingBalance;
    @Column(name = "interest_rate")
    private double interestRate;
    @Enumerated(EnumType.STRING)
    private LoanStatus status;
    @Column(name = "approval_date")
    private LocalDateTime approvalDate;
    @Column(name = "repayment_date")
    private LocalDateTime repaymentDate;


    public enum LoanStatus {
        PENDING,
        APPROVED,
        REJECTED,
        REPAID
    }
}
