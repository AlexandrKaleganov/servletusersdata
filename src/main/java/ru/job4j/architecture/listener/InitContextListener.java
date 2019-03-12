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
    private static final Logger LOGGER = Logger.getLogger(DbinitAdres.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Class.forName(sce.getServletContext().getInitParameter("driver"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        DbinitAdres adres = new DbinitAdres();
        adres.addtoDataTableInfo();
        System.out.println("init addres");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            Class.forName(sce.getServletContext().getInitParameter("driver"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        DbinitAdres adres = new DbinitAdres();
        try {
            adres.deleteAllInfo();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
