package io.kabootar.simulator.exceptions;

import org.springframework.http.HttpStatus;

public class IntentionalFailureException extends RuntimeException {
    public HttpStatus getErrorCode() {
        return errorCode;
    }

    private final HttpStatus errorCode;

    public IntentionalFailureException(String message, int errorCode) {
        super(message);
        if(errorCode == 0){
            this.errorCode = HttpStatus.valueOf(errorCode);
        } else {
            this.errorCode = HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
