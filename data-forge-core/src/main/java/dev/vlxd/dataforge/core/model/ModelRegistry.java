package dev.vlxd.dataforge.core.model;

import dev.vlxd.dataforge.api.DataOrigin;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
@Component
@ConditionalOnProperty(prefix = "data-forge", name = "enabled", havingValue = "true")
public class ModelRegistry {

    private final Map<String, Model<? extends ModelLoader<? extends DataOrigin<?, ?>>>> models = new HashMap<>();

    public Model<? extends ModelLoader<? extends DataOrigin<?, ?>>> getModel(String name) {
        return models.get(name);
    }

    public void addModel(Model<? extends ModelLoader<? extends DataOrigin<?, ?>>> model) {
        models.put(model.getName(), model);
    }
}
