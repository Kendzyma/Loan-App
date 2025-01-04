package com.loanapp.utils;

/**
 * @author :  Tiamiyu Kehinde
 * @project : Loan-App
 * @date :  1/3/25
 * @email :TiamiyuKehinde5@gmail.com
 */
public class LoanCalculator {
    public static double calculateInterestRate(double amount, int tenureMonths,double defaultRate) {

        if (amount > 100_000) {
            defaultRate += 0.01;
        }

        if (tenureMonths > 60) {
            defaultRate += 0.005;
        }

        return defaultRate;
    }

    public static double calculateSimpleInterestAmount(double principal, double annualRate, int tenureMonths) {
        double tenureInYears = tenureMonths / 12.0;
        double interest = principal * (annualRate/100) * tenureInYears;
        return round(principal + interest,2);
    }


    public static double round(double value, int decimals) {
        double scale = Math.pow(10, decimals);
        return Math.round(value * scale) / scale;
    }
}
