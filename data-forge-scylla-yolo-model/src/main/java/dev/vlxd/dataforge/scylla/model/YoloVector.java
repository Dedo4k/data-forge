package dev.vlxd.dataforge.scylla.model;

import dev.vlxd.dataforge.api.Feature;
import dev.vlxd.dataforge.api.Vector;

import java.util.List;

public class YoloVector implements Vector {
    @Override
    public List<Feature<?>> getFeatures() {
        return List.of();
    }
}
