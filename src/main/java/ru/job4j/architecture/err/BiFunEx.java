package ru.job4j.architecture.err;

public interface BiFunEx<R, L, E> {
    E apply(R r, L l) throws Exception;
}
