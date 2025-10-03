package dev.vlxd.dataforge.core.exception;

public class UnknownPipelineException extends DataForgeException {
    public UnknownPipelineException(String message) {
        super(message);
    }

    public UnknownPipelineException(String message, Object... args) {
        super(message, args);
    }
}
