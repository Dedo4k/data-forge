package dev.vlxd.dataforge.scylla.model.pipeline.stage;

import dev.vlxd.dataforge.api.pipeline.PipelineStageConfig;

import java.util.ArrayList;
import java.util.List;

public class ObjectDeletionStageConfig implements PipelineStageConfig {

    private final List<String> classnames;

    public ObjectDeletionStageConfig() {
        this.classnames = new ArrayList<>();
    }

    public ObjectDeletionStageConfig(List<String> classnames) {
        this.classnames = classnames;
    }

    public static class ObjectDeletionStageConfigBuilder {
        private final ObjectDeletionStageConfig config;

        public ObjectDeletionStageConfigBuilder() {
            this.config = new ObjectDeletionStageConfig();
        }

        public ObjectDeletionStageConfigBuilder withClassnames(List<String> values) {
            config.classnames.addAll(values);
            return this;
        }

        public ObjectDeletionStageConfig build() {
            return config;
        }
    }
}
