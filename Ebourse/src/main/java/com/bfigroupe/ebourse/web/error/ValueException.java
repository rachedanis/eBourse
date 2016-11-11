package com.bfigroupe.ebourse.web.error;

public class ValueException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366287163L;

    public ValueException() {
        super();
    }

    public ValueException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ValueException(final String message) {
        super(message);
    }

    public ValueException(final Throwable cause) {
        super(cause);
    }

}