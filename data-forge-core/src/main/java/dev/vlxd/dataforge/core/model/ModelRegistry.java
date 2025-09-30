package dev.vlxd.dataforge.core.model;

import dev.vlxd.dataforge.api.TokenOrigin;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
@Component
@ConditionalOnProperty(prefix = "data-forge", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ModelRegistry {

    private final Map<String, Model<? extends ModelLoader<? extends TokenOrigin<?, ?>>>> models = new HashMap<>();

    public Model<? extends ModelLoader<? extends TokenOrigin<?, ?>>> getModel(String name) {
        return models.get(name);
    }

    public void addModel(Model<? extends ModelLoader<? extends TokenOrigin<?, ?>>> model) {
        models.put(model.getName(), model);
    }
}
