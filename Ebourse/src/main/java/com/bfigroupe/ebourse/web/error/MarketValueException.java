package com.bfigroupe.ebourse.web.error;

public class MarketValueException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366287163L;

    public MarketValueException() {
        super();
    }

    public MarketValueException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MarketValueException(final String message) {
        super(message);
    }

    public MarketValueException(final Throwable cause) {
        super(cause);
    }

}