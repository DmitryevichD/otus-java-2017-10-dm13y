package by.dm13y.study.services.web.filters;

import by.dm13y.study.auth.User;
import by.dm13y.study.auth.UserChecker;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "authFilter", urlPatterns = "/info")
public class AuthFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)servletRequest;
        HttpServletResponse resp = (HttpServletResponse)servletResponse;

        User user = (User) req.getSession().getAttribute("user");
        if(user == null){
            resp.sendRedirect("/login.html");
        }else if(!UserChecker.isValid(user)){
            resp.sendRedirect("/login.html");
        }else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
