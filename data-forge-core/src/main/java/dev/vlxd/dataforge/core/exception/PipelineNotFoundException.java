package dev.vlxd.dataforge.core.exception;

public class PipelineNotFoundException extends DataForgeException {
    public PipelineNotFoundException(String message) {
        super(message);
    }

    public PipelineNotFoundException(String message, Object... args) {
        super(message, args);
    }
}
