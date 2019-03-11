package ru.job4j.architecture.err;


/**
 * функциональный итерфейс, с исключениями
 */
public interface ExtResources<T> {

    T read(String name) throws Exception;
    void write(T value) throws Exception;
}
