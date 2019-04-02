package ru.job4j.architecture.listener;

/**
 * контекст листинер для инициализации базы даннх адресов
 */

import org.apache.log4j.Logger;
import ru.job4j.architecture.dbmanagementuser.DbinitAdres;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
