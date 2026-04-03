package com.rishi.finance_dashboard_system.exception;

import com.rishi.finance_dashboard_system.util.ApiResponse;
import com.sun.jdi.request.DuplicateRequestException;
import jakarta.annotation.Resource;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    public static class ResourceNotFoundException extends  RuntimeException{
        public ResourceNotFoundException(String message){
            super(message);
        }

    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleBadRequest(UsernameNotFoundException exception){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(404,exception.getMessage(),null));

    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleDuplicate(IllegalArgumentException exception) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                body(new ApiResponse<>(400,exception.getMessage(),null));

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleException(Exception exception){
             exception.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(500,"Internal server error",null));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFound(ResourceNotFoundException exception){

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(404,exception.getMessage(),null));
    }


}
