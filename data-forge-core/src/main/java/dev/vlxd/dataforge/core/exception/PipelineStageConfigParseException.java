package dev.vlxd.dataforge.core.exception;

public class PipelineStageConfigParseException extends DataForgeException {

    public PipelineStageConfigParseException(String message) {
        super(message);
    }

    public PipelineStageConfigParseException(String message, Object... args) {
        super(message, args);
    }
}
