package dev.vlxd.dataforge.core.model;

import dev.vlxd.dataforge.api.Token;
import dev.vlxd.dataforge.api.TokenOrigin;

public interface Model<D extends Token<?, ?, ?, ?>, O extends TokenOrigin<D, ?>, L extends ModelLoader<O>> {

    String getName();

    L getLoader();

    Class<D> getTokenType();

    Class<O> getOriginType();
}
