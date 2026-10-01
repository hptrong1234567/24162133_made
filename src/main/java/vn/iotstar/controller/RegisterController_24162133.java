package vn.iotstar.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.model.User_24162133;
import vn.iotstar.service.OtpService_24162133;
import vn.iotstar.service.UserService_24162133;
import vn.iotstar.service.impl.OtpServiceImpl_24162133;
import vn.iotstar.service.impl.UserServiceImpl_24162133;

@WebServlet(urlPatterns = {"/register"})
public class RegisterController_24162133 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final UserService_24162133 userService = new UserServiceImpl_24162133();
    private final OtpService_24162133 otpService = new OtpServiceImpl_24162133();

    // ===== HIỂN THỊ TRANG ĐĂNG KÝ =====
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
    }

    // ===== XỬ LÝ ĐĂNG KÝ =====
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String email = req.getParameter("email");
        String fullname = req.getParameter("fullname");
        String phoneStr = req.getParameter("phone");
        String passwd = req.getParameter("passwd");
        String confirmPasswd = req.getParameter("confirmPasswd");

        // ===== VALIDATE =====
        String error = null;

        if (email == null || email.trim().isEmpty()) {
            error = "Email không được để trống";
        } else if (fullname == null || fullname.trim().isEmpty()) {
            error = "Họ tên không được để trống";
        } else if (passwd == null || passwd.length() < 6) {
            error = "Mật khẩu tối thiểu 6 ký tự";
        } else if (!passwd.equals(confirmPasswd)) {
            error = "Mật khẩu xác nhận không đúng";
        } else if (userService.existsByEmail(email.trim())) {
            error = "Email đã được sử dụng";
        }

        if (error != null) {
            req.setAttribute("error", error);
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            return;
        }

        // ===== TẠO USER (enabled = false tạm) =====
        User_24162133 user = new User_24162133();
        user.setEmail(email.trim());
        user.setFullname(fullname.trim());
        if (phoneStr != null && !phoneStr.isEmpty()) {
            try {
                user.setPhone(Integer.parseInt(phoneStr));
            } catch (NumberFormatException e) {
                user.setPhone(null);
            }
        }
        user.setPasswd(passwd);
        user.setSignupDate(LocalDateTime.now());
        user.setIsAdmin(false);

        // Insert user
        userService.insert(user);

        // ===== GỬI OTP QUA EMAIL =====
        try {
            otpService.sendRegisterOtp(email.trim());
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Không thể gửi OTP: " + e.getMessage());
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            return;
        }

        // ===== REDIRECT SANG TRANG VERIFY OTP =====
        resp.sendRedirect(req.getContextPath() + "/verify-otp?email=" + email);
    }
}