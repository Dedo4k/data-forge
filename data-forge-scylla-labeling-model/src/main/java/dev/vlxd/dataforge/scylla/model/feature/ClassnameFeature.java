package dev.vlxd.dataforge.scylla.model.feature;

import dev.vlxd.dataforge.api.BaseFeature;

public class ClassnameFeature extends BaseFeature<String> {

    public ClassnameFeature(String value) {
        super("classname", value);
    }
}
