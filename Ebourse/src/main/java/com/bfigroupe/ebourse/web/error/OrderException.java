package com.bfigroupe.ebourse.web.error;

public class OrderException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366287163L;

    public OrderException() {
        super();
    }

    public OrderException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public OrderException(final String message) {
        super(message);
    }

    public OrderException(final Throwable cause) {
        super(cause);
    }

}