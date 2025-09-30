package dev.vlxd.dataforge.core.exception;

public class PipelineStageExecutionException extends DataForgeException {
    public PipelineStageExecutionException(String message) {
        super(message);
    }

    public PipelineStageExecutionException(String message, Object... args) {
        super(message, args);
    }
}
