package dev.vlxd.dataforge.api;

public class BaseFeature<T> implements Feature<T> {

    private String name;
    private T value;

    public BaseFeature(String name, T value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public T getValue() {
        return value;
    }
}
