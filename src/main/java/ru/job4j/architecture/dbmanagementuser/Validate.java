package ru.job4j.architecture.dbmanagementuser;

/**
 * интерфейс класса, который будет выполнять проверку возможно ли данное деиствие и возвращять
 * результат решил сделать так: если получается выполнить действие
 * то всё норм
 * иначе вылетает ошибка и она обрабатываестся
 */

import ru.job4j.architecture.modeluser.Users;

import java.util.List;


public interface Validate<E> {
    E add(E users) throws DatabaseException;

    E update(E users) throws DatabaseException;

    E delete(E users) throws DatabaseException;

    List<E> findAll();

    E findById(E users) throws DatabaseException;

    List<E> deleteALL();

    E findByMail(E users);

    Boolean isCredentional(Users users);

    List<String> findAllcountry();

    List<String> findAllcity(E adres);

    List<String> findAllroles(String roles);


}
