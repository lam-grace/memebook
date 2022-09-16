package com.purple.handler;

import com.purple.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleInvalidTraceIdException
            (Exception ex, WebRequest request) {
        Map<String,String> errors = new HashMap<>();
        errors.put(ex.getClass().getName(),ex.getMessage());
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        errors.put("stack",sw.toString());
        ErrorResponse error = new ErrorResponse(errors);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
