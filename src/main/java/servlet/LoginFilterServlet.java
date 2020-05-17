package servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilterServlet implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        boolean loggedIn = session != null && session.getAttribute("loggedAsAdmin") != null;

        String loginURI = req.getContextPath() + "/login";
        boolean loginRequest = req.getRequestURI().equals(loginURI);

        if ((loggedIn && (Boolean) session.getAttribute("loggedAsAdmin") == true) || loginRequest) {
            chain.doFilter(req, resp);
        } else if (loggedIn && (Boolean) session.getAttribute("loggedAsAdmin") == false && !loginRequest && session.getAttribute("homePage") != null) {
            String homePage = (String) session.getAttribute("homePage");
            String editURI = req.getRequestURI() + "?id=" + req.getParameter("id");
            if (homePage.equals(editURI)) {
                chain.doFilter(req, resp);
            } else {
                resp.sendRedirect(homePage);
            }
        } else {
            resp.sendRedirect(loginURI);
        }
    }
}