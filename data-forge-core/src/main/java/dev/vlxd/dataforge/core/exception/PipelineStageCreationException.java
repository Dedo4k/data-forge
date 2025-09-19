package dev.vlxd.dataforge.core.exception;

public class PipelineStageCreationException extends DataForgeException {
    public PipelineStageCreationException(String message) {
        super(message);
    }

    public PipelineStageCreationException(String message, Object... args) {
        super(message, args);
    }
}
