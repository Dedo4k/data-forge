package dev.vlxd.dataforge.scylla.model.feature;

import dev.vlxd.dataforge.api.BaseFeature;

public class PoseFeature extends BaseFeature<String> {

    public PoseFeature(String value) {
        super("pose", value);
    }
}
