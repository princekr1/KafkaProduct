package com.appsdeveloperblog.ws.emailnotification.error;

public class RetryableException extends RuntimeException{

    public RetryableException(String message, Throwable cause) {
        super(message, cause);
    }

    public RetryableException(Throwable cause) {
        super(cause);
    }


    public RetryableException(String message) {
        super(message);
    }
}
