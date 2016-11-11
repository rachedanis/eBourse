package com.bfigroupe.ebourse.web.error;

public class ExecutionException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366287163L;

    public ExecutionException() {
        super();
    }

    public ExecutionException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ExecutionException(final String message) {
        super(message);
    }

    public ExecutionException(final Throwable cause) {
        super(cause);
    }

}