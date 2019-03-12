package ru.job4j.architecture.dbmanagement;

/**
 * Наша личная ошибка по работе с базой
 */
public class DatabaseException extends Exception {
    public DatabaseException(String s) {
        super(s);
    }
}
