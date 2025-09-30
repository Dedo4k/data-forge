package dev.vlxd.dataforge.api;

public interface Token<D, V extends Vector, I extends TokenIdentifier<?, ?>, O extends TokenOrigin<?, ?>> {

    D getToken();

    V getVector();

    I getIdentifier();

    O getOrigin();
}
