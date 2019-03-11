package ru.job4j.architecture.listener;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import ru.job4j.architecture.DbStore;
import ru.job4j.architecture.DbinitAdres;
import ru.job4j.architecture.err.BiConEx;
import ru.job4j.architecture.err.FunEx;
import ru.job4j.architecture.err.TriplexConEx;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.*;
import java.util.*;

public class InitContextListener implements ServletContextListener {
//    private final Map<Class<?>, TriplexConEx<Integer, PreparedStatement, Object>> dispat = new HashMap<>();
//    private HashMap<String, ArrayList<String>> map;
//    private static final Logger LOGGER = Logger.getLogger(DbinitAdres.class);
//    private Connection conn;
//    @Override
//    public void contextInitialized(ServletContextEvent sce) {
//        this.dispat.put(Integer.class, (index, ps, value) -> ps.setInt(index, (Integer) value));
//        this.dispat.put(String.class, (index, ps, value) -> ps.setString(index, (String) value));
//        ServletContext ctx = sce.getServletContext();
//        try {
//            this.init(ctx.getInitParameter("url"), ctx.getInitParameter("login"), ctx.getInitParameter("pass"), ctx.getInitParameter("driver"));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        this.addtoDataTableInfo();
//        System.out.println("init addres");
//    }
//
//
//
//
//    private void init(String url, String login, String pass, String driver) throws SQLException, ClassNotFoundException {
//        this.conn = DriverManager.getConnection(url,
//                login, pass);
//        conn.setAutoCommit(false);
//        Class.forName(driver);
//    }
//
//    private void loadtoMAp() throws IOException {
//        URL url = new URL("https://raw.githubusercontent.com/David-Haim/CountriesToCitiesJSON/master/countriesToCities.json");
//        BufferedReader rider = new BufferedReader(new InputStreamReader(url.openStream()));
//        String temp;
//        StringBuilder bilder = new StringBuilder();
//        while ((temp = rider.readLine()) != null) {
//            bilder.append(temp);
//        }
//        ObjectMapper mapper = new ObjectMapper();
//        this.map = mapper.readValue(bilder.toString(), HashMap.class);
//    }
//
//    private void addtoDataTableInfo() {
//        try {
//            loadtoMAp();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            map.forEach((key, listValue) -> {
//                if (!key.equals("") && this.isIndex("select * from country where country = ?", Arrays.asList(key)) == 0) {
//                    Integer idCountry = this.updateInfo("insert into country(country) values(?)", Arrays.asList(key));
//                    listValue.forEach(city -> {
//                        if (!city.equals("")) {
//                            this.updateInfo("insert into city(city, country_id) values(?, ?)", Arrays.asList(city, idCountry));
//                        }
//                    });
//                }
//            });
//        } catch (Exception e) {
//            LOGGER.error(e.getMessage(), e);
//        } finally {
//            try {
//                conn.commit();
//            } catch (SQLException e) {
//                try {
//                    conn.rollback();
//                } catch (SQLException e1) {
//                    LOGGER.error(e.getMessage(), e);
//                }
//            }
//        }
//
//    }
//
//    private Integer updateInfo(String command, List<Object> att) {
//        return this.db(command, att, ps -> {
//            Integer k = 0;
//            ps.executeUpdate();
//            try (ResultSet resultSet = ps.getGeneratedKeys()) {
//                while (resultSet.next()) {
//                    k = resultSet.getInt(1);
//                }
//            } catch (SQLException e) {
//                LOGGER.error(e.getMessage(), e);
//            }
//            return k;
//        }).get();
//    }
//
//    private Integer isIndex(String command, List<Object> att) {
//        return this.db(command, att, ps -> {
//            Integer k = 0;
//            try (ResultSet resultSet = ps.executeQuery()) {
//                while (resultSet.next()) {
//                    k = resultSet.getInt("id");
//                }
//            } catch (SQLException e) {
//                LOGGER.error(e.getMessage(), e);
//            }
//            return k;
//        }).get();
//    }
//
//    /**
//     * рефакторинг для добавления объектов в бд
//     *
//     * @param sql
//     * @param param
//     * @param fun
//     * @param <R>
//     * @return
//     */
//
//    private <R> Optional<R> db(String sql, List<Object> param, FunEx<PreparedStatement, R> fun) {
//        Optional<R> rsl = Optional.empty();
//        try (PreparedStatement pr = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//            this.forIdex(param, (index, value) -> dispat.get(value.getClass()).accept(index + 1, pr, value));
//            rsl = Optional.ofNullable(fun.apply(pr));
//        } catch (Exception e) {
//            LOGGER.error(e.getMessage(), e);
//        }
//        return rsl;
//    }
//
//    private <T> void forIdex(List<T> list, BiConEx<Integer, T> metod) throws Exception {
//        for (int i = 0; i < list.size(); i++) {
//            metod.accept(i, list.get(i));
//        }
//    }
//
//    public void deleteAllInfo() throws SQLException {
//        this.updateInfo("delete from adreshelp", new ArrayList<>());
//        this.updateInfo("delete from city", new ArrayList<>());
//        this.updateInfo("delete from country", new ArrayList<>());
//        conn.commit();
//    }

}
