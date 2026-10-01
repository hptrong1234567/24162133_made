package vn.iotstar.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.util.Constant_24162133;

@WebServlet(urlPatterns = {"/logout"})
public class LogoutController_24162133 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // ===== HỦY SESSION =====
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // ===== XÓA COOKIE =====
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (Constant_24162133.COOKIE_REMEMBER.equals(cookie.getName())) {
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    resp.addCookie(cookie);
                }
            }
        }

        System.out.println(">>> Đã đăng xuất");

        // ===== REDIRECT VỀ LOGIN =====
        resp.sendRedirect(req.getContextPath() + "/login?logout=true");
    }
}