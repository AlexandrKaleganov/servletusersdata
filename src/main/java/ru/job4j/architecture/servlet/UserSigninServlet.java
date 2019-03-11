package ru.job4j.architecture.servlet;


import ru.job4j.architecture.DispatchDiapason;
import ru.job4j.architecture.model.Err;
import ru.job4j.architecture.model.Users;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserSigninServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/loginIN.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (DispatchDiapason.getInstance().access("isCredentional",
                    new Users ("0",  "nam", req.getParameter("mail"), req.getParameter("pass"),
                            req.getParameter("country"), req.getParameter("city")),
                    true)) {
                req.getSession().setAttribute("login", req.getParameter("mail"));
                resp.sendRedirect(String.format("%s/", req.getContextPath()));
            } else {
                req.setAttribute("err", new Err ("erro login and password"));
                req.getRequestDispatcher("/WEB-INF/views/loginIN.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
