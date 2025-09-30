package dev.vlxd.dataforge.scylla.model;

import dev.vlxd.dataforge.api.Feature;
import dev.vlxd.dataforge.api.Vector;
import dev.vlxd.dataforge.scylla.model.feature.ClassnameFeature;
import dev.vlxd.dataforge.scylla.model.feature.DifficultFeature;
import dev.vlxd.dataforge.scylla.model.feature.PoseFeature;
import dev.vlxd.dataforge.scylla.model.feature.TruncatedFeature;
import lombok.Getter;

import java.util.List;

@Getter
public class CropVector implements Vector {

    private ClassnameFeature classname;
    private PoseFeature pose;
    private TruncatedFeature truncated;
    private DifficultFeature difficult;

    public CropVector() {
    }

    @Override
    public List<Feature<?>> getFeatures() {
        return List.of(classname, pose, truncated, difficult);
    }

    public CropVector classname(String classname) {
        this.classname = new ClassnameFeature(classname);
        return this;
    }

    public CropVector pose(String pose) {
        this.pose = new PoseFeature(pose);
        return this;
    }

    public CropVector truncated(Integer truncated) {
        this.truncated = new TruncatedFeature(truncated);
        return this;
    }

    public CropVector difficult(Integer difficult) {
        this.difficult = new DifficultFeature(difficult);
        return this;
    }
}
