package by.dm13y.study.services.web.servlets;

import by.dm13y.study.auth.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="/info")
public class CashInfoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User)req.getSession().getAttribute("user");
        Cookie cookie = new Cookie("user_id", user.getId());
        resp.addCookie(cookie);
        req.getRequestDispatcher("/info.html").forward(req, resp);
    }

}
