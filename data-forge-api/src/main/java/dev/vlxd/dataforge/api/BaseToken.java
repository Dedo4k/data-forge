package dev.vlxd.dataforge.api;

public abstract class BaseToken<D, V extends Vector, I extends TokenIdentifier<?, ?>, O extends TokenOrigin<?, ?>> implements Token<D, V, I, O> {

    protected D token;
    protected V vector;
    protected I identifier;
    protected O origin;

    public BaseToken(D token, V vector, I identifier, O origin) {
        this.token = token;
        this.vector = vector;
        this.identifier = identifier;
        this.origin = origin;
    }

    @Override
    public D getToken() {
        return token;
    }

    @Override
    public I getIdentifier() {
        return identifier;
    }

    @Override
    public O getOrigin() {
        return origin;
    }

    @Override
    public V getVector() {
        return vector;
    }
}
