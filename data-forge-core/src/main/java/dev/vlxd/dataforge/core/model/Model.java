package dev.vlxd.dataforge.core.model;

public interface Model<L extends ModelLoader<?>> {

    String getName();

    L getLoader();
}
