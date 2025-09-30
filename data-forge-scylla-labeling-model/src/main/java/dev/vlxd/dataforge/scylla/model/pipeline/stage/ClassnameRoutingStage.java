package dev.vlxd.dataforge.scylla.model.pipeline.stage;

import dev.vlxd.dataforge.core.exception.PipelineStageConfigParseException;
import dev.vlxd.dataforge.core.pipeline.BasePipelineStage;
import dev.vlxd.dataforge.core.pipeline.PipelineContext;
import dev.vlxd.dataforge.scylla.model.CropOrigin;
import dev.vlxd.dataforge.scylla.model.mapping.LayoutObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unchecked", "rawtypes"})
public class ClassnameRoutingStage extends BasePipelineStage<CropOrigin, ClassnameRoutingStageConfig> {

    public static final String NAME = "classnameRoutingStage";

    private static final String CLASSNAMES_PARSE_ERROR = "Failed to parse config {}. classnames should consist of strings";

    public ClassnameRoutingStage(Map<String, Object> configMap, PipelineContext pipelineContext) {
        ClassnameRoutingStageConfig.ClassnameRoutingStageConfigBuilder stageConfigBuilder = ClassnameRoutingStageConfig.builder();
        List<ClassnameRoutingStageConfig.PipelineRoute> routes = new ArrayList<>();
        Object routesObj = configMap.get("routes");
        if (routesObj instanceof Map) {
            Map<String, Map<String, Object>> routesConfigs = (Map<String, Map<String, Object>>) routesObj;
            for (Map.Entry<String, Map<String, Object>> routeConfig : routesConfigs.entrySet()) {
                ClassnameRoutingStageConfig.PipelineRoute.PipelineRouteBuilder routeBuilder = ClassnameRoutingStageConfig.PipelineRoute.builder();
                Object classnamesObj = routeConfig.getValue().get("classnames");
                if (classnamesObj instanceof Map classnames) {
                    routeBuilder.classnames(classnames.values().stream()
                            .map(value -> {
                                if (value instanceof String str) {
                                    return str;
                                } else {
                                    throw new PipelineStageConfigParseException(CLASSNAMES_PARSE_ERROR, configMap);
                                }
                            }).toList());
                }
                Object targetObj = routeConfig.getValue().get("target");
                if (targetObj instanceof String target) {
                    routeBuilder.target(target);
                }
                routes.add(routeBuilder.build());
            }
        }
        this.config = stageConfigBuilder.routes(routes).build();
        this.pipelineContext = pipelineContext;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public ClassnameRoutingStageConfig getConfig() {
        return config;
    }

    @Override
    public CropOrigin execute(CropOrigin data) {
        for (ClassnameRoutingStageConfig.PipelineRoute route : config.getRoutes()) {
            for (LayoutObject object : data.getAnnotation().getObjects()) {
                if (route.getClassnames().contains(object.getName())) {
                    pipelineContext.getPipelineManager().getPipeline(route.getTarget()).execute(data);
                }
            }
        }

        return nextStage != null ? nextStage.execute(data) : null;
    }

    @Override
    public void getResult() {

    }
}
