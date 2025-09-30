package dev.vlxd.dataforge.api;

import java.util.List;

public interface TokenOrigin<D extends Token<?, ?, ?, ?>, I extends TokenIdentifier<?, ?>> {

    void loadOrigin();

    void unloadOrigin();

    boolean isLoaded();

    D getToken(I identifier);

    List<D> getTokens(List<I> identifiers);

    List<D> loadTokens();
}
