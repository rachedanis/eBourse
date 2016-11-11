package com.bfigroupe.ebourse.web.error;

public class UserException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366287163L;

    public UserException() {
        super();
    }

    public UserException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserException(final String message) {
        super(message);
    }

    public UserException(final Throwable cause) {
        super(cause);
    }

}