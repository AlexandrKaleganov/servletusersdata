package ru.job4j.architecture.err;

public interface FunEx<E, R> {

    R apply(E e) throws Exception;
}
