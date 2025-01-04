package com.loanapp.exception;

/**
 * @author :  Tiamiyu Kehinde
 * @project : Loan-App
 * @date :  1/3/25
 * @email : Tiamiyu@getrova.com, TiamiyuKehinde5@gmail.com
 */
public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}
