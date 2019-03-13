package ru.job4j.architecture.servlet;


import ru.job4j.architecture.dbmanagement.DispatchDiapason;
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
            Users temp = new Users();
            temp.setMail(req.getParameter("mail"));
            temp.setPassword(req.getParameter("pass"));
            if (DispatchDiapason.getInstance().access("isCredentional",
                    temp,
                    true)) {
                req.getSession().setAttribute("login", req.getParameter("mail"));
                req.setAttribute("roles", DispatchDiapason.getInstance().access("findbyMail", temp).getRoles());
                resp.sendRedirect(String.format("%s/", req.getContextPath()));
            } else {
                req.setAttribute("err", new Err("error login and password"));
                req.getRequestDispatcher("/WEB-INF/views/loginIN.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
