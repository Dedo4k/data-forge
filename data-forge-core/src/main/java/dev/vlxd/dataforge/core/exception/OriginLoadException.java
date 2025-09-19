package dev.vlxd.dataforge.core.exception;

public class OriginLoadException extends DataForgeException {
    public OriginLoadException(String message) {
        super(message);
    }

    public OriginLoadException(String message, Object... args) {
        super(message, args);
    }
}
