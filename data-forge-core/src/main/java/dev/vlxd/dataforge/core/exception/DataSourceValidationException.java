package dev.vlxd.dataforge.core.exception;

public class DataSourceValidationException extends DataForgeException {
    public DataSourceValidationException(String message) {
        super(message);
    }

    public DataSourceValidationException(String message, Object... args) {
        super(message, args);
    }
}
