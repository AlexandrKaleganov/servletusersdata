package ru.job4j.architecture;

import ru.job4j.architecture.err.FunEx;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * методы переделаны на copyonwritearraylist
 */
public class MemoryStore implements Store<Users> {
    private final CopyOnWriteArrayList<Users> database = new CopyOnWriteArrayList<>();
    private static final MemoryStore INSTANCE = new MemoryStore();

    public static MemoryStore getInstance() {
        return INSTANCE;
    }

    MemoryStore() {
        this.initRoot();
    }

    private void initRoot() {
        if (this.findByMail(new Users("0", "root", "root", "root", "root", "root")).getMail() == null) {
            this.add(new Users("0", "root", "root", "root", "root", "root"));
        }
    }

    @Override
    public Users add(Users users) {
        this.database.add(users);
        this.database.get(this.database.size() - 1).setId("" + (this.database.size() - 1));
        return this.database.get(this.database.size() - 1);
    }

    @Override
    public Users update(Users users) {
        if (users.getName().length() > 0) {
            this.database.get(Integer.valueOf(users.getId())).setName(users.getName());
        }
        if (users.getMail().length() > 0) {
            this.database.get(Integer.valueOf(users.getId())).setMail(users.getMail());
        }
        return this.findById(users);
    }

    /**
     * вернёт старый объект
     *
     * @param users
     * @return
     */
    @Override
    public Users delete(Users users) {
        return this.db(users, Integer.parseInt(users.getId()), i ->
                this.database.remove((int) i)).orElse(new Users());
    }


    @Override
    public CopyOnWriteArrayList<Users> findAll() {
        return this.database;
    }

    @Override
    public Users findById(Users users) {
        return this.db(users, Integer.parseInt(users.getId()), i ->
                this.database.get(i)).orElse(new Users());
    }

    /**
     * метод очистки бд был создан для тестов
     *
     * @return
     */
    @Override
    public List<Users> deleteALL() {
        this.database.clear();
        return this.findAll();
    }

    /**
     * поиск пользоваеля по логину
     *
     * @param users
     * @return
     */
    @Override
    public Users findByMail(Users users) {
        return this.db(users, users, (users1) -> {
            Users rs = null;
            for (Users us : this.database) {
                if (us.getMail().contains(users1.getMail())) {
                    rs = us;
                    break;
                }
            }
            return rs;
        }).orElse(new Users());
    }

    @Override
    public List<String> findAllcountry() {
        return null;
    }

    @Override
    public List<String> findAllcity(Users country) {
        return null;
    }


    @Override
    public boolean isCredentional(Users users) {
        Boolean rsl = false;
        for (Users test : this.database) {
            if (test.getMail().equals(users.getMail())) {
                if (test.getPassword().equals(users.getPassword())) {
                    rsl = true;
                }
                break;
            }
        }
        return rsl;
    }

    /**
     * метод проверяет по всем полям пользователя
     *
     * @param datausers
     * @param users
     * @return
     */
    private boolean isADD(Users datausers, Users users) {
        return this.isValid(datausers.getName(), users.getName()) && this.isValid(datausers.getMail(), users.getMail());
    }

    /**
     * метод проверяет совпадение стринговых значений
     *
     * @param datausers
     * @param users
     * @return
     */
    private boolean isValid(String datausers, String users) {
        boolean rsl = true;
        if (users != null) {
            rsl = false;
        } else if (datausers.contains(users)) {
            rsl = false;
        }
        return rsl;
    }


    private <R, K> Optional<R> db(R users, K i, FunEx<K, R> funEx) {
        Optional<R> rsl = Optional.empty();
        try {
            rsl = Optional.of(funEx.apply(i));
        } catch (Exception e) {
            rsl = Optional.empty();
        }
        return rsl;
    }

}
