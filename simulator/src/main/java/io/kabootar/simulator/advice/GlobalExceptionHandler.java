package io.kabootar.simulator.advice;


import io.kabootar.simulator.exceptions.IntentionalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IntentionalFailureException.class)
    public ResponseEntity<String> handleIntentionalFailure(
            IntentionalFailureException ex) {

        return ResponseEntity
                .status(ex.getErrorCode())
                .body(ex.getMessage());
    }
}
