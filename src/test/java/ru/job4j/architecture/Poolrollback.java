package ru.job4j.architecture;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class Poolrollback extends BasicDataSource {
    @Override
    public Connection getConnection() throws SQLException {
        return ConnectionRollback.create(super.getConnection());
    }
}
