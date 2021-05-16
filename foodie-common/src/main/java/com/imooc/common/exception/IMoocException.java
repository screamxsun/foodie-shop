package com.imooc.common.exception;

public class IMoocException extends RuntimeException{
    public IMoocException() {
        super();
    }

    public IMoocException(String message) {
        super(message);
    }

    public IMoocException(String message, Throwable cause) {
        super(message, cause);
    }

    public IMoocException(Throwable cause) {
        super(cause);
    }

    protected IMoocException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
