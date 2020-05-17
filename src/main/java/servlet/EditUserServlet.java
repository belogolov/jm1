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

@WebServlet("/edit")
public class EditUserServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        this.userService = Service.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (req.getParameter("id") == null) {
            resp.setStatus(400);
            return;
        }
        User user;
        try {
            long id = Long.parseLong(req.getParameter("id"));
            user = userService.getUserById(id);
        } catch (NumberFormatException | DBException e) {
            resp.setStatus(400);
            throw new IOException(e);
        }
        req.setAttribute("user", user);
        req.getRequestDispatcher("/pages/editUser.jsp").forward(req, resp);
        resp.setContentType("text/html;charset=utf-8");
        resp.setStatus(200);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (req.getParameter("OK") != null) {
            try {
                userService.updateUser(getUser(req));
            } catch (NumberFormatException | DBException e) {
                resp.setStatus(400);
                throw new IOException(e);
            }
        }
        resp.sendRedirect(req.getContextPath() + "/admin");
        resp.setStatus(200);
    }

    private User getUser(HttpServletRequest req) throws NumberFormatException {
        long id = Long.parseLong(req.getParameter("id"));
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        return new User(id, name, email, password);
    }

}
