package com.loanapp.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author :  Tiamiyu Kehinde
 * @project : Loan-App
 * @date :  1/3/25
 * @email : Tiamiyu@getrova.com, TiamiyuKehinde5@gmail.com
 */

@Slf4j
public class CommonUtils {

    public static void jsonLogger(String message,Object obj){
        if (obj != null) {
            try {
                String jsonString = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(obj);
                log.info(message, jsonString);
            } catch (JsonProcessingException e) {
                log.error("Failed to convert object to JSON string: {}", obj, e);
            } catch (Exception e) {
                log.error("Unexpected error occurred while logging object: {}", obj, e);
            }
        }else {
            log.info(message, (Object) null);
        }
    }
}
