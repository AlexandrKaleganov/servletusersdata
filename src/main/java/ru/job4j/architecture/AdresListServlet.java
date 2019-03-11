package ru.job4j.architecture;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AdresListServlet extends HttpServlet {
    private static final org.apache.log4j.Logger LOGGER = Logger.getLogger(AdresListServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json; charset=utf-8");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        ObjectMapper mapper = new ObjectMapper();
        try {
            ArrayList<String> arrayList = DispatchDiapason.getInstance().access(req.getParameter("action"), new Users(req.getParameter("id"),
                    req.getParameter("name"), req.getParameter("mail"), req.getParameter("pass"),
                    req.getParameter("country"), req.getParameter("city")), new ArrayList<String>());
            writer.append(mapper.writeValueAsString(arrayList));
            writer.flush();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            req.setAttribute("err", new Err(e.getMessage(), LocalDateTime.now()));
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }
}
