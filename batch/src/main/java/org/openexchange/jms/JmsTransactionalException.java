package org.openexchange.jms;

public class JmsTransactionalException extends Exception {
    public JmsTransactionalException(String message) {
        super(message);
    }

    public JmsTransactionalException(String message, Throwable cause) {
        super(message, cause);
    }
}
