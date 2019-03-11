package ru.job4j.architecture.err;

public interface ConEx<E> {
    void accept(E e) throws Exception;
}

