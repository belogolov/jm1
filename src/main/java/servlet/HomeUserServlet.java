package servlet;

import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user")
public class HomeUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        if (session == null && session.getAttribute("user") == null) {
            resp.setStatus(400);
            return;
        }
        req.setAttribute("user", (User) session.getAttribute("user"));
        req.getRequestDispatcher("/pages/homeUser.jsp").forward(req, resp);
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(200);
    }

}
