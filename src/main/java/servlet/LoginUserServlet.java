package servlet;

import exception.DBException;
import model.Role;
import model.User;
import service.Service;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

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
            if (userService.isValidUser(email, req.getParameter("password"))) {
                User user = userService.getUserByLogin(email);
                Set<Role> roles = userService.getRoles(user);
                Role admin = userService.getRoleById(1L);
                if (roles.contains(admin)) {
                    req.getSession().setAttribute("loggedAsAdmin", new Boolean(true));
                    resp.sendRedirect(req.getContextPath() + "/admin");
                    //req.getRequestDispatcher("/admin").forward(req, resp);
                } else {
                    req.getSession().setAttribute("loggedAsAdmin", new Boolean(false));
                    String homePage = "/edit?id=" + user.getId();
                    req.getSession().setAttribute("homePage", homePage);
//                    req.setAttribute("user", user);
//                    req.getRequestDispatcher("/pages/editUser.jsp").forward(req, resp);
                    resp.sendRedirect(req.getContextPath() + homePage);
                }
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
