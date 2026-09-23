package com.example.tickets.exceptions;

public class TicketsSoldeOutException extends EventTicketException{
    public TicketsSoldeOutException(String message) {
        super(message);
    }

    public TicketsSoldeOutException() {
    }

    public TicketsSoldeOutException(String message, Throwable cause) {
        super(message, cause);
    }

    public TicketsSoldeOutException(Throwable cause) {
        super(cause);
    }

    public TicketsSoldeOutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
