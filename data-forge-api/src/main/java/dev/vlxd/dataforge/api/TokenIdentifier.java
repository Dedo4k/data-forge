package dev.vlxd.dataforge.api;

public interface TokenIdentifier<D extends Token<?, ?, ?, ?>, O extends TokenOrigin<?, ?>> {

    D identify(O origin);
}
