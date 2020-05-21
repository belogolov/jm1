package servlet;

import exception.DBException;
import model.User;
import service.Service;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginUserServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        this.userService = Service.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
        resp.setStatus(200);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String email = req.getParameter("email");
        try {
            User user = userService.validUser(email, req.getParameter("password"));
            if (user != null) {
                req.getSession().setAttribute("roles", userService.getRoles(user));
                req.getSession().setAttribute("admin", userService.getRoleById(1L));
                req.getSession().setAttribute("user", user);
                resp.sendRedirect(req.getContextPath() + "/admin");
                resp.setStatus(200);
            } else {
                req.getRequestDispatcher("/index.jsp").forward(req, resp);
                resp.setStatus(400);
            }
        } catch (DBException e) {
            resp.setStatus(400);
            throw new IOException(e);
        }

    }

}
