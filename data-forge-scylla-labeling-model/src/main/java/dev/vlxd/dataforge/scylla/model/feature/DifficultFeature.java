package dev.vlxd.dataforge.scylla.model.feature;

import dev.vlxd.dataforge.api.BaseFeature;

public class DifficultFeature extends BaseFeature<Integer> {

    public DifficultFeature(Integer value) {
        super("difficult", value);
    }
}
