package com.bfigroupe.ebourse.web.error;

public class BankAccountException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366287163L;

    public BankAccountException() {
        super();
    }

    public BankAccountException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public BankAccountException(final String message) {
        super(message);
    }

    public BankAccountException(final Throwable cause) {
        super(cause);
    }

}