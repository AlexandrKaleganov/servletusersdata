package ru.job4j.architecture.model;

/**
 * роли пользователя если есть жалание добавить роли, то необходимо сделать скрипт обновления БД
 * который добавит ещё какие либо роли в базу данных
 * также их необходимо прописать здесь иначем можно будет наткнуться на гору ошибок
 */
public enum Roles {
    ADMIN, USER
}
