package ru.job4j.architecture;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(DispatchDiapason.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * как я это понял
     *
     * @param req
     * @param res
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        HttpServletRequest request = (HttpServletRequest) req;    //два запроса переделываем под HttpServlet
        HttpServletResponse response = (HttpServletResponse) res;
        if (request.getRequestURI().contains("/signin")) {     //если лезим на страницу авторизации - то
            chain.doFilter(req, res);                           //фильтр нас пропускает и наши запросы де нас в свою очередь перекинет на loginIN.jsp
        } else {
            if (request.getSession().getAttribute("login") == null) {   //если в сессии нет  атрибута login
                response.sendRedirect(String.format("%s/signin", request.getContextPath())); //то нас опять бросит на сервлет signin где перекинет на loginIN.jsp
                return;                                                                  //и дальше метод завершится
            }
            if (req.getParameter("exit") != null) {
                request.getSession().invalidate();
                response.sendRedirect(String.format("%s/signin", request.getContextPath())); //то нас опять бросит на сервлет signin где перекинет на loginIN.jsp
                return;
            }
            chain.doFilter(req, res);   //а вот если всё пучком и запрос не на страницу авторизации и сессия содержит логин то фильтр нас пропускает куда угодно
        }
    }

    @Override
    public void destroy() {

    }

}
