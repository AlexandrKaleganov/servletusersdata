package ru.job4j.architecture.dbmanagementuser;

import ru.job4j.architecture.model.Users;

import java.util.List;

/**
 * интерфейс класса, который будет напрямую работать с нашей БД
 */


public interface Store<T> {
    T add(T users);

    T update(T users);

    T delete(T users);

    List<T> findAll();

    T findById(T users);

    List<T> deleteALL();

    T findByMail(T users);

    boolean isCredentional(Users users);

    List<String> findAllcountry();

    List<String> findAllcity(T country);


    List<String> findAllroles();

}
