package dev.vlxd.dataforge.scylla.model.pipeline.stage;

import dev.vlxd.dataforge.api.pipeline.PipelineStageConfig;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ClassnameValidationStageConfig implements PipelineStageConfig {
    private final List<String> blackList;
    private final List<String> whiteList;

    public ClassnameValidationStageConfig() {
        this.blackList = new ArrayList<>();
        this.whiteList = new ArrayList<>();
    }

    public ClassnameValidationStageConfig(List<String> blackList, List<String> whiteList) {
        this.blackList = blackList;
        this.whiteList = whiteList;
    }

    public static class ClassnameValidationStageConfigBuilder {
        private final ClassnameValidationStageConfig config;

        public ClassnameValidationStageConfigBuilder() {
            this.config = new ClassnameValidationStageConfig();
        }

        public ClassnameValidationStageConfigBuilder withBlackList(List<String> values) {
            config.blackList.addAll(values);
            return this;
        }

        public ClassnameValidationStageConfigBuilder withWhiteList(List<String> values) {
            config.whiteList.addAll(values);
            return this;
        }

        public ClassnameValidationStageConfig build() {
            return config;
        }
    }
}
