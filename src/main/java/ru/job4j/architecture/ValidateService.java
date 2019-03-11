package ru.job4j.architecture;

import ru.job4j.architecture.err.DatabaseException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Alexander Kaleganov
 * @version 4
 * @since 04/12/2018
 * этот класс прослойка между сервлетами и логикой программы
 * методы этого класса будут проверять: можно ли добавить этот объект,
 * в каждом методе будем проверять что id у нас соответствует формату
 * поля пользователя тоже должны соответствовать формату
 */
public class ValidateService implements Validate<Users> {
    private final Store<Users> logic = DbStore.getInstance();
    private static final ValidateService INSTANCE = new ValidateService();

    public static ValidateService getInstance() {
        return INSTANCE;
    }

    /**
     * в методе если не выпали исключения то в базу будет добавлен пользователь
     * первый метод может выбросить сообщение неверный формат id
     * второй выбросит исключение если имя или логин неверного формата или пустые
     * третий выбросит исключение если пользователь уже есть в БД или если ЛОГИН ЕСТЬ В БД
     */
    @Override
    public Users add(Users users) throws DatabaseException {
        this.isIdFORMAT(users);
        this.isNameLoginFORMAT(users, 2);
        this.validation(users, (u) -> !u.getPassword().matches("[a-zA-Z, 0-9]{4,20}"), "Error Password");
        this.validation(users, u -> !(this.logic.findByMail(u).getMail() == null), "Пользователь с таким логином уже существует");
        return this.logic.add(users);
    }

    /**
     * обращаю внимание на то что метод апдейт может прининимать имя и логин с пустыми значениям
     * если пустые значения обновляться не будут
     *
     * @param
     * @param users
     * @return
     */
    @Override
    public Users update(Users users) throws DatabaseException {
        this.isIdFORMAT(users);
        this.isNameLoginFORMAT(users, 0);
        this.validation(users, u -> !(this.logic.findByMail(u).getMail() == null), "Пользователь с таким логином уже существует");
        return this.logic.update(users);
    }

    /**
     * при удалении поля имени и логина могут быть пустыми
     *
     * @param users
     * @return
     * @throws DatabaseException
     */
    @Override
    public Users delete(Users users) throws DatabaseException {
        this.isIdFORMAT(users);
        return this.logic.delete(users);
    }

    @Override
    public List<Users> findAll() {
        return this.logic.findAll();
    }

    /**
     * если k == null то будет исключение
     *
     * @param users
     * @return
     * @throws DatabaseException
     */
    @Override
    public Users findById(Users users) throws DatabaseException {
        this.isIdFORMAT(users);
        return this.logic.findById(users);
    }

    @Override
    public List<Users> deleteALL() {
        return this.logic.deleteALL();
    }

    /**
     * функциональный метод на нём будут основаны наши проверки
     *
     * @param u
     * @param isValid
     * @param error
     * @throws DatabaseException
     */
    private void validation(Users u, Predicate<Users> isValid, String error) throws DatabaseException {
        if (isValid.test(u)) {
            throw new DatabaseException(String.format("error = %s", error));
        }
    }

    /**
     * проверяет формат id
     *
     * @param users
     * @throws DatabaseException
     */
    private void isIdFORMAT(Users users) throws DatabaseException {
        this.validation(users, (u) -> !u.getId().matches("[0-9]{1,10}"), "id_format");
    }

    /**
     * проверяет формат имени и логина
     *
     * @param users
     * @throws DatabaseException
     */
    private void isNameLoginFORMAT(Users users, int start) throws DatabaseException {
        this.validation(users, (u) -> !u.getName().matches("[a-zA-Z]{" + start + ",20}|[а-яА-Я]{" + start + ",20}"), "USERNAME");
        this.validation(users, (u) -> !u.getMail().matches("[a-zA-Z, 0-9]{" + start + ",20}"), "LOGIN");
    }


    @Override
    public Boolean isCredentional(Users users) {
        return this.logic.isCredentional(users);
    }

    public ArrayList<String> findAllcountry() {
        return (ArrayList<String>) this.logic.findAllcountry();
    }
    public ArrayList<String> findAllcity(Users country) {
        return (ArrayList<String>) this.logic.findAllcity(country);
    }
}
