package com.loanapp.dto;

/**
 * @author :  Tiamiyu Kehinde
 * @project : Loan-App
 * @date :  1/3/25
 * @email : TiamiyuKehinde5@gmail.com
 */
public class DefaultResponse {
    private boolean success;
    private int code;

    public DefaultResponse() {
        this.success = true;
        this.code = 200;
    }
}
