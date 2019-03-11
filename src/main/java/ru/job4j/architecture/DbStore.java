package ru.job4j.architecture;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.architecture.err.BiConEx;
import ru.job4j.architecture.err.FunEx;
import ru.job4j.architecture.err.TriplexConEx;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class DbStore implements Store<Users> {
    //не стал делать поле статичным, т.к. иначе не зню как прикрутить тесты

    private BasicDataSource source;
    private static final DbStore INSTANCE = new DbStore();
    private final Map<Class<?>, TriplexConEx<Integer, PreparedStatement, Object>> dispat = new HashMap<>();
    private static final Logger LOGGER = Logger.getLogger(DbStore.class);

    public DbStore() {
        this.source = new BasicDataSource();
        this.init();
        this.dispat.put(Integer.class, (index, ps, value) -> ps.setInt(index, (Integer) value));
        this.dispat.put(String.class, (index, ps, value) -> ps.setString(index, (String) value));
        this.addTable();
        this.initRoot();
    }

    private void initRoot() {
        if (this.findByMail(new Users("0", "root", "root", "root", "", "")).getMail() == null) {
            this.add(new Users("0", "root", "root", "root", "", ""));
        }
    }
    /**
     * добавление таблицы
     */
    public void addTable() {
        try {
            Properties settings = new Properties();
            try (InputStream in = DbStore.class.getClassLoader().getResourceAsStream("gradle.properties")) {
                settings.load(in);
            }
            db(settings.getProperty("add.tableUser"), new ArrayList<>(), pr -> pr.executeUpdate());
            db(settings.getProperty("add.tableCountry"), new ArrayList<>(), pr -> pr.executeUpdate());
            db(settings.getProperty("add.tableCity"), new ArrayList<>(), pr -> pr.executeUpdate());
            db(settings.getProperty("add.tableAdresHelp"), new ArrayList<>(), pr -> pr.executeUpdate());
            db(settings.getProperty("add.tableUserview"), new ArrayList<>(), pr -> pr.executeUpdate());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * метод инициализации объекта
     */
    private void init() {
        try {
            Properties settings = new Properties();
            try (InputStream in = DbStore.class.getClassLoader().getResourceAsStream("gradle.properties")) {
                settings.load(in);
            }
            this.source.setDriverClassName(settings.getProperty("db.driver"));
            this.source.setUrl(settings.getProperty("db.host"));
            this.source.setUsername(settings.getProperty("db.login"));
            this.source.setPassword(settings.getProperty("db.password"));
            this.source.setMinIdle(5);
            this.source.setMaxIdle(10);
            this.source.setMaxOpenPreparedStatements(100);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public DbStore(BasicDataSource source) {
        this.source = source;
        this.dispat.put(Integer.class, (index, ps, value) -> ps.setInt(index, (Integer) value));
        this.dispat.put(String.class, (index, ps, value) -> ps.setString(index, (String) value));
        this.addTable();
        this.initRoot();
    }

    public static DbStore getInstance() {
        return INSTANCE;
    }

    /**
     * в цикле перебираем лист с параметкрами
     *
     * @param list
     * @param metod
     * @throws Exception
     * @paramd <T>
     */
    private <T> void forIdex(List<T> list, BiConEx<Integer, T> metod) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            metod.accept(i, list.get(i));
        }
    }

    /**
     * избавляемся от трай кетч мне почему то не понравилось какая реализация была в видео,
     * исключение мы всёравно отловим по этому здесь я сделал по своему
     *
     * @param sql
     * @param param
     * @param fun
     * @return
     */
    private <R> Optional<R> db(String sql, List<Object> param, FunEx<PreparedStatement, R> fun) {
        Optional<R> rsl = Optional.empty();
        try (Connection conn = source.getConnection();
             PreparedStatement pr = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            this.forIdex(param, (index, value) -> dispat.get(value.getClass()).accept(index + 1, pr, value));
            rsl = Optional.ofNullable(fun.apply(pr));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return rsl;
    }

    /**
     * рефоктор поиска индексов по запросам
     */
    private Integer isIndex(String command, List<Object> att) {
        return this.db(command, att, ps -> {
            Integer k = 0;
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    k = resultSet.getInt("id");
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }
            return k;
        }).get();
    }

    /**
     * добавление данных в таблицы и получение индекса
     *
     * @param command
     * @param att
     * @return
     */
    private Integer updateInfo(String command, List<Object> att) {
        return this.db(command, att, ps -> {
            Integer k = 0;
            ps.executeUpdate();
            try (ResultSet resultSet = ps.getGeneratedKeys()) {
                while (resultSet.next()) {
                    k = resultSet.getInt(1);
                }
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }
            return k;
        }).get();
    }

    /**
     * если id нулевой то будем добавлять запись в таблицу и получать id
     *
     * @param command
     * @param id
     * @return
     */
    private Integer isnotNullId(String command, List<Object> att, Integer id) {
        if (id == 0) {
            return updateInfo(command, att);
        } else {
            return id;
        }
    }

    @Override
    public Users add(Users user) {
        Integer country = isIndex("select * from country where country = ?", Arrays.asList(user.getCountry()));
        Integer city = isIndex("select * from city where city = ?", Arrays.asList(user.getCity()));
        country = isnotNullId("insert into country(country) values(?)", Arrays.asList(user.getCountry()), country);
        city = isnotNullId("insert into city(city, country_id) values(?, ?)", Arrays.asList(user.getCity(), country), city);
        this.db(
                "insert into users (name, mail, pass) values (?, ?, ?)",
                Arrays.asList(user.getName(), user.getMail(), user.getPassword()),
                ps -> {
                    ps.executeUpdate();
                    try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            user.setId(String.valueOf(generatedKeys.getInt(1)));
                        }
                    } catch (SQLException e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                    return user;
                }
        );
        this.updateInfo("insert into adreshelp(user_id, country_id, city_id) values(?, ?, ?)", Arrays.asList(Integer.valueOf(user.getId()), country, city));
        return user;
    }

    @Override
    public Users update(Users users) {
        this.updateInfo("UPDATE users SET NAME = ?, mail = ?, pass =? where users.id = ? ",
                Arrays.asList(users.getName(), users.getMail(), users.getPassword(), Integer.valueOf(users.getId())));
        Integer indexCountry = isIndex("select * from country where country = ?", Arrays.asList(users.getCountry()));
        Integer indexCity = isIndex("select * from city where city = ?", Arrays.asList(users.getCity()));
        this.updateInfo("UPDATE adreshelp SET country_id = ?, city_id = ? where user_id = ? ",
                Arrays.asList(indexCountry, indexCity, Integer.valueOf(users.getId())));
        return this.findById(users);
    }

    @Override
    public Users delete(Users users) {
        Users rsl = this.findById(users);
        this.updateInfo("delete from adreshelp where user_id = ?", Arrays.asList(Integer.valueOf(users.getId())));
        this.updateInfo("delete from users where users.id = ? ", Arrays.asList(Integer.valueOf(users.getId())));
        return rsl;
    }

    @Override
    public List<Users> findAll() {
        return this.db(
                "select * from userview", new ArrayList<>(),
                ps -> {
                    ArrayList<Users> rsl = new ArrayList<>();
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            if (!rs.getString("mail").equals("root")) {
                                rsl.add(new Users(String.valueOf(rs.getInt("id")),
                                        rs.getString("name"), rs.getString("mail"), rs.getString("pass"),
                                        rs.getString("country"), rs.getString("city")));
                            }
                        }
                    } catch (SQLException e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                    return rsl;
                }
        ).get();
    }

    /**
     * метод очистки бд
     */
    @Override
    public List<Users> deleteALL() {
        this.db("delete from adreshelp;", new ArrayList<>(), pr -> pr.executeUpdate());
        this.db("delete from city;", new ArrayList<>(), pr -> pr.executeUpdate());
        this.db("delete from country;", new ArrayList<>(), pr -> pr.executeUpdate());
        this.db("delete from users;", new ArrayList<>(), pr -> pr.executeUpdate());
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
        return this.db(
                "select * from userview where mail = ?", Arrays.asList(users.getMail()),
                ps -> {
                    Users res = null;
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            res = new Users(String.valueOf(rs.getInt("id")),
                                    rs.getString("name"), rs.getString("mail"), "",
                                    rs.getString("country"), rs.getString("city"));
                        }
                    } catch (SQLException e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                    return res;
                }
        ).orElse(new Users());
    }

    /**
     * проверяет есть ли пользователь с таким логином и паролем
     *
     * @param users
     * @return
     */
    @Override
    public boolean isCredentional(Users users) {
        return this.db(
                "select * from users where mail = ? and pass = ?", Arrays.asList(users.getMail(), users.getPassword()),
                ps -> {
                    Boolean res = false;
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            res = true;
                        }
                    } catch (SQLException e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                    return res;
                }
        ).get();
    }

    @Override
    public Users findById(Users users) {
        return this.db(
                "select * from userview where id = ?", Arrays.asList(Integer.valueOf(users.getId())),
                ps -> {
                    Users res = null;
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            res = new Users(String.valueOf(rs.getInt("id")),
                                    rs.getString("name"), rs.getString("mail"), "",
                                    rs.getString("country"), rs.getString("city"));
                        }
                    } catch (SQLException e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                    return res;
                }
        ).orElse(new Users());
    }

    /**
     * получение списка всех стран из базы
     * @return
     */
    public List<String> findAllcountry() {
        return this.db("select * from country", new ArrayList<>(), ps -> {
            ArrayList<String> rsl = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rsl.add(rs.getString(2));
                }

            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }
            return rsl;
        }).get();
    }
    /**
     * получение списка всех городов в сооответствии со страной из базы
     * @return
     */
    public List<String> findAllcity(Users country) {
        Integer id = this.isIndex("select * from country where country = ?", Arrays.asList(country.getCountry()));
        return this.db("select * from city where country_id = ?", Arrays.asList(id), ps -> {
            ArrayList<String> rsl = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rsl.add(rs.getString(2));
                }

            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
            }
            return rsl;
        }).get();
    }
}