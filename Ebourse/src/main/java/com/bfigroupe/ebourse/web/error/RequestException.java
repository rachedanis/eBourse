package com.bfigroupe.ebourse.web.error;

public class RequestException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366287163L;

    public RequestException() {
        super();
    }

    public RequestException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public RequestException(final String message) {
        super(message);
    }

    public RequestException(final Throwable cause) {
        super(cause);
    }

}