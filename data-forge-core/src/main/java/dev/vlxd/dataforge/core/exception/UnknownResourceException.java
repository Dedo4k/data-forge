package dev.vlxd.dataforge.core.exception;

public class UnknownResourceException extends DataForgeException {
    public UnknownResourceException(String message) {
        super(message);
    }

    public UnknownResourceException(String message, Object... args) {
        super(message, args);
    }
}
