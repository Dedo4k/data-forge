package dev.vlxd.dataforge.core.exception;

public class ModelNotFoundException extends DataForgeException {
    public ModelNotFoundException(String message) {
        super(message);
    }

    public ModelNotFoundException(String message, Object... args) {
        super(message, args);
    }
}
