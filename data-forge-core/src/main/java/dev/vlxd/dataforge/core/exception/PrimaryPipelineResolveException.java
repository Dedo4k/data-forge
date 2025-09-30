package dev.vlxd.dataforge.core.exception;

public class PrimaryPipelineResolveException extends DataForgeException {
    public PrimaryPipelineResolveException(String message) {
        super(message);
    }

    public PrimaryPipelineResolveException(String message, Object... args) {
        super(message, args);
    }
}
