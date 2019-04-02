package ru.job4j.architecture.servlet;

/**
 * сервлет делает получает пользователя по id  и делает
 * редерект на JSP  /WEB-INF/views/edit.jsp для того чтобы
 * видеть данне которые хочешь редактировать
 */

import org.apache.log4j.Logger;
import ru.job4j.architecture.dbmanagementuser.DispatchDiapason;
import ru.job4j.architecture.modeluser.Err;
import ru.job4j.architecture.modeluser.Users;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class UserUpdateServlet extends HttpServlet {
    private static final org.apache.log4j.Logger LOGGER = Logger.getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.getRequestDispatcher("/WEB-INF/views/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        Users temp = new Users();
        temp.setId(req.getParameter("id"));
        try {
            req.setAttribute("u", DispatchDiapason.getInstance().access(req.getParameter("action"), temp));
            req.getRequestDispatcher("/WEB-INF/views/edit.jsp").forward(req, resp);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            req.setAttribute("err", new Err(e.getMessage(), LocalDateTime.now()));
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }
}
