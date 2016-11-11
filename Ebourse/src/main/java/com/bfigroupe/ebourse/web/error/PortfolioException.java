package com.bfigroupe.ebourse.web.error;

public class PortfolioException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366287163L;

    public PortfolioException() {
        super();
    }

    public PortfolioException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PortfolioException(final String message) {
        super(message);
    }

    public PortfolioException(final Throwable cause) {
        super(cause);
    }

}