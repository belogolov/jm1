package servlet;

import exception.DBException;
import model.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/edit")
public class EditUserServlet extends HttpServlet {
    UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (req.getParameter("id") == null) {
            req.setAttribute("user", new User());
            req.setAttribute("description", "New user");
        } else {
            long id = Long.parseLong(req.getParameter("id"));
            User user;
            try {
                user = userService.getUserById(id);
            } catch (DBException e) {
                resp.setStatus(400);
                throw new IOException(e);
            }
            req.setAttribute("user", user);
            req.setAttribute("description", "Edit user");
        }
        req.getRequestDispatcher("/pages/editUser.jsp").forward(req, resp);
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(200);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (req.getParameter("OK") != null) {
            long id = 0;
            if (req.getParameter("id") != null) {
                id = Long.parseLong(req.getParameter("id"));
            }
            if (id > 0) {
                try {
                    userService.updateUser(getUser(req, id));
                } catch (DBException e) {
                    resp.setStatus(400);
                    throw new IOException(e);
                }
            } else {
                try {
                    userService.addUser(getUser(req, 0));
                } catch (DBException e) {
                    resp.setStatus(400);
                    throw new IOException(e);
                }
            }
        }
        resp.sendRedirect(req.getContextPath() + "/all");
        resp.setStatus(200);
    }

    private User getUser(HttpServletRequest req, long id) {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        return new User(id, name, email, password);
    }

}
