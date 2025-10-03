package dev.vlxd.dataforge.core.model;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
@Component
@SuppressWarnings("rawtypes")
@ConditionalOnProperty(prefix = "data-forge", name = "enabled", havingValue = "true", matchIfMissing = true)
public class ModelRegistry {

    private final Map<String, Model> models = new HashMap<>();

    public Model getModel(String name) {
        return models.get(name);
    }

    public void addModel(Model model) {
        models.put(model.getName(), model);
    }
}
