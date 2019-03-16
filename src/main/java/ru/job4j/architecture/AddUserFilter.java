package ru.job4j.architecture;

import ru.job4j.architecture.model.Err;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class AddUserFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        HttpServletRequest request = (HttpServletRequest) req;    //два запроса переделываем под HttpServlet
        HttpServletResponse response = (HttpServletResponse) res;
        if (!request.getSession().getAttribute("roles").toString().contains("ADMIN")) {     //если лезим на страницу авторизации - то
            req.setAttribute("err", new Err("У вас не достаточно прав, братитесь к админитратору", LocalDateTime.now()));
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, res);
        }
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }
}
