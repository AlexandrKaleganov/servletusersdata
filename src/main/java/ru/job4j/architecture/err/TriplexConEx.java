package ru.job4j.architecture.err;

public interface TriplexConEx<E, R, K> {
    void accept(E e, R r, K k) throws Exception;
}
