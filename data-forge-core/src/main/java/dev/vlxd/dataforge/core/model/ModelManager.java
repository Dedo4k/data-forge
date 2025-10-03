package dev.vlxd.dataforge.core.model;

import dev.vlxd.dataforge.core.configuration.DataForgeConfigurationProperties;
import dev.vlxd.dataforge.core.exception.ModelNotFoundException;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
@SuppressWarnings("rawtypes")
public class ModelManager {

    private final ModelRegistry modelRegistry;
    private final Model activeModel;

    public ModelManager(DataForgeConfigurationProperties properties,
                        ModelRegistry modelRegistry) {
        this.modelRegistry = modelRegistry;

        String modelName = properties.getModel();
        Model model = modelRegistry.getModel(modelName);

        if (model == null) {
            throw new ModelNotFoundException("Model {} not found in registry", modelName);
        }

        this.activeModel = model;
    }
}
