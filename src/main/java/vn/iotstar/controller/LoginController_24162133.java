package vn.iotstar.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.model.User_24162133;
import vn.iotstar.service.UserService_24162133;
import vn.iotstar.service.impl.UserServiceImpl_24162133;
import vn.iotstar.util.Constant_24162133;

@WebServlet(urlPatterns = {"/login"})
public class LoginController_24162133 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final UserService_24162133 userService = new UserServiceImpl_24162133();

    // ===== HIỂN THỊ TRANG LOGIN =====
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Nếu đã đăng nhập → chuyển sang home
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute(Constant_24162133.SESSION_USER) != null) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }

    // ===== XỬ LÝ ĐĂNG NHẬP =====
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String email = req.getParameter("email");
        String passwd = req.getParameter("passwd");

        // ===== VALIDATE =====
        if (email == null || email.trim().isEmpty()
                || passwd == null || passwd.trim().isEmpty()) {
            req.setAttribute("error", "Email và mật khẩu không được để trống");
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
            return;
        }

        // ===== TÌM USER =====
        User_24162133 user = userService.findByEmail(email.trim());

        if (user == null || !user.getPasswd().equals(passwd)) {
            req.setAttribute("error", "Email hoặc mật khẩu không đúng");
            req.setAttribute("email", email);
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
            return;
        }

        // ===== CẬP NHẬT LAST LOGIN =====
        user.setLastLogin(LocalDateTime.now());
        userService.update(user);

        // ===== TẠO SESSION =====
        HttpSession session = req.getSession(true);
        session.setAttribute(Constant_24162133.SESSION_USER, user);
        session.setAttribute(Constant_24162133.SESSION_USERNAME, user.getEmail());
        session.setMaxInactiveInterval(30 * 60);  // 30 phút

        // ===== LƯU COOKIE REMEMBER =====
        Cookie cookie = new Cookie(Constant_24162133.COOKIE_REMEMBER, user.getEmail());
        cookie.setMaxAge(30 * 60);  // 30 phút
        cookie.setPath("/");
        resp.addCookie(cookie);

        System.out.println(">>> Đăng nhập thành công: " + user.getEmail()
                + " | Admin: " + user.getIsAdmin());

        // ===== REDIRECT THEO ROLE =====
        if (Boolean.TRUE.equals(user.getIsAdmin())) {
            resp.sendRedirect(req.getContextPath() + "/admin/books");
        } else {
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }
}