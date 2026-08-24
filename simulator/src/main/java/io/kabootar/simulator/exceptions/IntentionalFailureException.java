package io.kabootar.simulator.exceptions;

public class IntentionalFailureException extends RuntimeException {
    public IntentionalFailureException(String message) {
        super(message);
    }
}
