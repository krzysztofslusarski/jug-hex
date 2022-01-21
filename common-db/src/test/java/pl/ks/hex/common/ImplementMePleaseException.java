package pl.ks.hex.common;

public class ImplementMePleaseException extends RuntimeException {
    public ImplementMePleaseException() {
    }

    public ImplementMePleaseException(String message) {
        super(message);
    }

    public ImplementMePleaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImplementMePleaseException(Throwable cause) {
        super(cause);
    }

    public ImplementMePleaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
