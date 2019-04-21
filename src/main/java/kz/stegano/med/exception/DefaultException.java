package kz.stegano.med.exception;

public class DefaultException extends RuntimeException {

    private String message;

    public DefaultException(String message) {
        this.message = message;
    }
}
