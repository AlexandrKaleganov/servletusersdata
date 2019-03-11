package ru.job4j.architecture;
/**
 * класс диспетчер комманд
 * в хешмапе содержаться три команды
 * add, update, delete  и реализованы они через лямбы
 * с помощью функционального интерфейса
 * глядя на тот шаблон, который предоставляется в задании я ничего лучше не смог придумать надеюсь подход правильный
 * метод init  инициализирует нашу мапу
 * метод access получает доступ к нужной функции на входи идёт валидатор, ключ и созданный пользователь
 */

import org.apache.log4j.Logger;
import ru.job4j.architecture.err.FunEx;

import java.util.*;

public class DispatchDiapason {
    /**
     * Dispatch.
     */

    private final Map<String, FunEx<Users, Optional>> dispatch = new HashMap<String, FunEx<Users, Optional>>();
    private final Validate validate = ValidateService.getInstance();
    private final static DispatchDiapason INSTANCE = new DispatchDiapason().init();
    private static final Logger LOGGER = Logger.getLogger(DispatchDiapason.class);

    public static DispatchDiapason getInstance() {
        return INSTANCE;
    }

    /**
     * Load initial handlers.
     *
     * @return current object.
     */
    public DispatchDiapason init() {
        this.dispatch.put("add", (users) ->
                Optional.of(this.validate.add(users))
        );

        this.dispatch.put("update", (users) ->
                Optional.of(this.validate.update(users))
        );
        this.dispatch.put("delete", (users) ->
                Optional.of(this.validate.delete(users))
        );
        this.dispatch.put("findall", (users) ->
                Optional.of(this.validate.findAll())
        );
        this.dispatch.put("findbyid", (users) ->
                Optional.of(this.validate.findById(users))

        );
        this.dispatch.put("deleteAll", (users) ->
                Optional.of(this.validate.deleteALL())
        );
        this.dispatch.put("isCredentional", (users) ->
                Optional.of(this.validate.isCredentional((Users) users)));
        this.dispatch.put("findAllcountry", (users) ->
                Optional.of(this.validate.findAllcountry()));
        this.dispatch.put("findAllcity", (users) ->
                Optional.of(this.validate.findAllcity(users)));
        return this;
    }

    /**
     * Check access for person by age.
     *
     * @return true if access are allowed
     */
    public Users access(String key, Users users) throws Exception {
        Optional<Users> rsl = Optional.empty();
        rsl = this.dispatch.get(key).apply(users);
        return rsl.get();
    }

    public <E> E access(String key, Users users, E param) throws Exception {
        Optional<E> rsl = Optional.empty();
        rsl = this.dispatch.get(key).apply(users);
        return rsl.get();
    }

}
