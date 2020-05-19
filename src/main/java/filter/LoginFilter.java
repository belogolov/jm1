package filter;

import model.Role;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

@WebFilter("/login")
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        boolean logged = session != null && session.getAttribute("roles") != null && session.getAttribute("admin") != null;
        String loginURI = req.getContextPath() + "/login";

        if (req.getRequestURI().equals(loginURI)) {
            chain.doFilter(request, response);
        } else if (logged) {
            Set<Role> roles = (Set<Role>) session.getAttribute("roles");
            Role admin = (Role) session.getAttribute("admin");
            if (roles.contains(admin)) {
                resp.sendRedirect(req.getContextPath() + "/admin");
            } else {
                resp.sendRedirect(req.getContextPath() + "/user");
            }
        } else {
            resp.sendRedirect(loginURI);
        }
    }
}