package dev.vlxd.dataforge.core.exception;

public class UnknownPipelineExecutorException extends DataForgeException {
    public UnknownPipelineExecutorException(String message) {
        super(message);
    }

    public UnknownPipelineExecutorException(String message, Object... args) {
        super(message, args);
    }
}
