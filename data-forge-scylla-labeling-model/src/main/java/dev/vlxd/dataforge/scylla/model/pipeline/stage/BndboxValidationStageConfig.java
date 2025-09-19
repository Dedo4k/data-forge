package dev.vlxd.dataforge.scylla.model.pipeline.stage;

import dev.vlxd.dataforge.api.pipeline.PipelineStageConfig;
import lombok.Getter;

@Getter
public class BndboxValidationStageConfig implements PipelineStageConfig {
    private Integer minWidth;
    private Integer minHeight;
    private Integer maxWidth;
    private Integer maxHeight;

    public BndboxValidationStageConfig() {
    }

    public BndboxValidationStageConfig(Integer minWidth, Integer minHeight, Integer maxWidth, Integer maxHeight) {
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
    }

    public static class BndboxValidationStageConfigBuilder {
        private final BndboxValidationStageConfig config;

        public BndboxValidationStageConfigBuilder() {
            this.config = new BndboxValidationStageConfig();
        }

        public BndboxValidationStageConfigBuilder withMinWidth(Integer value) {
            config.minWidth = value;
            return this;
        }

        public BndboxValidationStageConfigBuilder withMinHeight(Integer value) {
            config.minHeight = value;
            return this;
        }

        public BndboxValidationStageConfigBuilder withMaxWidth(Integer value) {
            config.maxWidth = value;
            return this;
        }

        public BndboxValidationStageConfigBuilder withMaxHeight(Integer value) {
            config.maxHeight = value;
            return this;
        }

        public BndboxValidationStageConfig build() {
            return config;
        }
    }
}
