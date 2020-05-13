package servlet;

import com.google.gson.Gson;
import exception.DBException;
import model.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/all")
public class AllUsersServlet extends HttpServlet {
    UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        refreshUsers(req, resp);
        resp.setContentType("text/html;charset=utf-8");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (req.getParameter("Add") != null) {
            resp.sendRedirect(req.getContextPath() + "/edit");
        } else if (req.getParameter("Edit") != null) {
            String id = req.getParameter("id");
            if (id != null) {
                resp.sendRedirect(req.getContextPath() + "/edit?id=" + id);
            } else {
                resp.sendRedirect(req.getContextPath() + "/edit");
            }
        } else if (req.getParameter("Delete") != null) {
            long id = getId(req);
            if (id > 0) {
                try {
                    userService.deleteUser(id);
                    refreshUsers(req, resp);
                } catch (DBException e) {
                    resp.setStatus(400);
                    throw new IOException(e);
                }
            }
        } else if (req.getParameter("Refresh") != null) {
            refreshUsers(req, resp);
        }
    }

    private void refreshUsers(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            req.setAttribute("listUsers", userService.getAllUsers());
        } catch (DBException e) {
            req.setAttribute("listUsers", new ArrayList<User>());
        }
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
        resp.setStatus(200);
    }

    private long getId(HttpServletRequest req) throws IOException {
        if (req.getParameter("id") == null) {
            return 0;
        }
        long id;
        try {
            id = Long.parseLong(req.getParameter("id"));
        } catch (NumberFormatException e) {
            throw new IOException("Incorrect ID", e);
        }
        return id;
    }

}
