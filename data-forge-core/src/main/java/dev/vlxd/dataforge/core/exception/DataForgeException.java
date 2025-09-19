package dev.vlxd.dataforge.core.exception;

import org.slf4j.helpers.MessageFormatter;

public class DataForgeException extends RuntimeException {
    public DataForgeException(String message) {
        super(message);
    }

    public DataForgeException(String message, Object... args) {
        super(MessageFormatter.arrayFormat(message, args).getMessage());
    }
}
