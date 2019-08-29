package com.saffetturkoglu.app.exception;


import java.util.Objects;

/** Exception for "Global Response" error. */
public class GlobalResponseException extends RuntimeException {
    private final String message;

    private final Throwable cause;

    public GlobalResponseException(String message) {
        super(message);
        this.cause = new Throwable(message);
        this.message = message;
    }

    protected GlobalResponseException(Throwable cause, String message) {
        super(message, cause);
        this.message = message;
        this.cause = cause;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GlobalResponseException)) return false;
        GlobalResponseException that = (GlobalResponseException) o;
        return Objects.equals(getMessage(), that.getMessage()) &&
                Objects.equals(getCause(), that.getCause());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMessage(), getCause());
    }
}
