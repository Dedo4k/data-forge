package dev.vlxd.dataforge.core.exception;

public class ResourceValidationException extends DataForgeException {
    public ResourceValidationException(String message) {
        super(message);
    }

    public ResourceValidationException(String message, Object... args) {
        super(message, args);
    }
}
