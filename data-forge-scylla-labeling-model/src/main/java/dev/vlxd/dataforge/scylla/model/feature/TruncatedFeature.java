package dev.vlxd.dataforge.scylla.model.feature;

import dev.vlxd.dataforge.api.BaseFeature;

public class TruncatedFeature extends BaseFeature<Integer> {

    public TruncatedFeature(Integer value) {
        super("truncated", value);
    }
}
