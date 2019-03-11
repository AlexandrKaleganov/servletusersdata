package ru.job4j.architecture;
/**
 * @author Alexander Kaleganov (alexmur07@mail.ru)
 * @version 8.0
 * @since 04-11-2018
 * сервлет делает редерект на JSP  /WEB-INF/views/index.jsp и решает за логику
 */


import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.time.LocalDateTime;


public class UserServlet extends HttpServlet {
    private final DispatchDiapason dispatsh = DispatchDiapason.getInstance();
    private static final org.apache.log4j.Logger LOGGER = Logger.getLogger(UserServlet.class);


    @Override
    public void init() throws ServletException {
        DbinitAdres adres = new DbinitAdres();
        adres.addtoDataTableInfo();
    }

    @Override
    public void destroy() {
        DbinitAdres adres = new DbinitAdres();
        try {
            adres.deleteAllInfo();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * закомментил за ненадобностью, т.к. за интерфейс теперь отвечает jsp
     */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        resp.setContentType("text/html;charset=UTF-8");
        req.setCharacterEncoding("UTF-8");
        Users users = new Users(req.getParameter("id"), req.getParameter("name"),
                req.getParameter("mail"), req.getParameter("password"),
                req.getParameter("country"), req.getParameter("city"));
        try {
            req.setAttribute("message", this.dispatsh.access(req.getParameter("action"),
                    users));
            req.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);
        } catch (Exception e) {
            StringWriter error = new StringWriter();
            e.printStackTrace(new PrintWriter(error));
            LOGGER.error(e.getMessage(), e);
            req.setAttribute("err", new Err(error.toString(), LocalDateTime.now()));
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }
}
