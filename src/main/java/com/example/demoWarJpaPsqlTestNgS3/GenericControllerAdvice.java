/**
 * 
 */
package com.example.demoWarJpaPsqlTestNgS3;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author x173117
 *
 */
@ControllerAdvice
public class GenericControllerAdvice {

    private static final Logger log = LoggerFactory.getLogger(GenericControllerAdvice.class);

    @ResponseBody
    @ExceptionHandler(Throwable.class)
    public Map<String, Object> throwableHandler(Throwable ex, HttpServletResponse response) {

        if (ex instanceof IllegalArgumentException) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        } else {
            response.setStatus(HttpStatus.FORBIDDEN.value());
        }
        log.error("erreur : ", ex);

        Map<String, Object> messageReturn = new HashMap<>();
        messageReturn.put("message", ex.getMessage());
        return messageReturn;
    }

}
