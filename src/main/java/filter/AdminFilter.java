package filter;

import model.Role;
import service.Service;
import service.UserService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

@WebFilter("/admin/*")
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        boolean logged = session != null && session.getAttribute("roles") != null && session.getAttribute("admin") != null;
        if (logged) {
            Set<Role> roles = (Set<Role>)session.getAttribute("roles");
            Role admin = (Role) session.getAttribute("admin");
            if (!roles.contains(admin)) {
                resp.sendRedirect(req.getContextPath() + "/user");
            } else {
                chain.doFilter(req, resp);
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}