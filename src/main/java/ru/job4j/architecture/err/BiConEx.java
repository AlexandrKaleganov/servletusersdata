package ru.job4j.architecture.err;

public interface BiConEx<L, R> {
    void accept(L left, R right) throws Exception;
}
