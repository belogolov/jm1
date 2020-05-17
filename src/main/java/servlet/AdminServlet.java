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
import java.util.ArrayList;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        this.userService = Service.getInstance();
    }

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
            resp.sendRedirect(req.getContextPath() + "/add");
        } else if (req.getParameter("Edit") != null && req.getParameter("id") != null) {
            resp.sendRedirect(req.getContextPath() + "/edit?id=" + req.getParameter("id"));
        } else if (req.getParameter("Delete") != null && req.getParameter("id") != null) {
            resp.sendRedirect(req.getContextPath() + "/delete?id=" + req.getParameter("id"));
        } else {
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
        req.getRequestDispatcher("/pages/listOfUsers.jsp").forward(req, resp);
        resp.setStatus(200);
    }

}
