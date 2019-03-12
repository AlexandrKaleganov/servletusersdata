package ru.job4j.architecture.dbmanagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import ru.job4j.architecture.err.BiConEx;
import ru.job4j.architecture.model.Users;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;


public class DbStoreTest {
    /**
     * метод рефакторинг в любом случае при тестировании мне надо будет подключиться к базе,
     * добавиитть туда объект , произвести тест метода, и очистить базу
     * с роллбаком фикгня получалась, т.к. у меня в каждом методе коннект закрывался
     * суть работы - функциональный метод получает готовую бд, и оъект который мы получили при добавлении,
     * чтобы по id  мы могли его обновить, удалить, и т.д... вообщем оттестировать
     * далее в блоке finally я добавил мето очистки данных из бд т.е. после
     * прохождения теста удачного или неудачного
     * я буду очищать БД
     *
     * @param fank
     */
    private void alltestfunc(BiConEx<DbStore, Users> fank) {
        Users users = new Users("12", "sacha", "alexmur07", "password", "Russia", "Novosibirsk", "ADMIN");
        DbStore dbStore = new DbStore(this.init());
        Users expected = dbStore.add(users);
        try {
            fank.accept(dbStore, expected);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbStore.deleteALL();
        }
    }

    private BasicDataSource init() {
        BasicDataSource source = new BasicDataSource();
        try {
            Properties settings = new Properties();
            try (InputStream in = DbStore.class.getClassLoader().getResourceAsStream("gradle.properties")) {
                settings.load(in);
            }
            source.setDriverClassName(settings.getProperty("db.driver"));
            source.setUrl(settings.getProperty("db.host"));
            source.setUsername(settings.getProperty("db.login"));
//            source.setPassword("");
            source.setMinIdle(5);
            source.setMaxIdle(10);
            source.setMaxOpenPreparedStatements(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return source;
    }

    /**
     * тестируем метод добавления в бд
     */
    @Test
    public void addDD() {
        this.alltestfunc((bd, exp) -> {
            Assert.assertThat(bd.findById(exp), Is.is(exp));
        });
    }

    /**
     * тестируем метод получения всех пользователй по id
     *
     * @throws SQLException
     */
    @Test
    public void findaaalTest() {
        this.alltestfunc((bd, exp) -> {
           assertThat(bd.findAll().get(0), Is.is(exp));
        });
    }

    /**
     * тестируем метод изменения пользователей
     */
    @Test
    public void updateTest() {
        this.alltestfunc((bd, exp) -> {
            bd.update(new Users(exp.getId(), "lex", "lex07", "psw", "Russia", "Novosibirsk", "ADMIN"));
            assertThat(bd.findById(exp).getName(), Is.is("lex"));
        });
    }

    /**
     * тестируем удаление пользователя из бд
     */
    @Test
    public void deleteTest() {
        this.alltestfunc((db, exp) -> {
            db.delete(exp).getId();
            assertThat(db.findById(exp).getId(), Is.is(new Users().getId()));
        });
    }

    @Test
    public void findByLogin() {
        this.alltestfunc((db, exp) -> {
            assertThat(db.findByMail(exp).getMail(), Is.is(exp.getMail()));
        });
    }

    @Test
    public void isCredentional() {
        this.alltestfunc((db, exp) -> {
            assertThat(db.isCredentional(new Users("12", "sacha", "alexmur07", "password", "Russia", "Novosibirsk", "ADMIN")), Is.is(true));
            assertThat(db.isCredentional(new Users("12", "sacha", "alexmu07", "password", "Russia", "Novosibirsk", "ADMIN")), Is.is(false));
            assertThat(db.isCredentional(new Users("12", "", "alexmur07", "ssword", "Russia", "Novosibirsk", "ADMIN")), Is.is(false));
        });
    }

    /**
     * JSON  города и страны
     */
    @Test
    public void testTestCity() throws IOException {
        URL url = new URL("https://raw.githubusercontent.com/David-Haim/CountriesToCitiesJSON/master/countriesToCities.json");
        StringBuilder bilder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String temp;
        while ((temp = reader.readLine()) != null) {
            bilder.append(temp);
        }
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, ArrayList<String>> mapa = mapper.readValue(bilder.toString(), HashMap.class);
        mapa.remove("");

    }

    @Test
    public void findAllcountry() {
        this.alltestfunc((db, user) ->
                assertThat(db.findAllcountry().get(1), Is.is("Russia")));
    }

    @Test
    public void findAllcity() {
        this.alltestfunc((db, user) ->
                assertThat(db.findAllcity(user).get(0), Is.is("Novosibirsk")));
    }
}