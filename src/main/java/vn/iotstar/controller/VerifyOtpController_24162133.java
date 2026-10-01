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

@WebServlet(urlPatterns = {"/verify-otp"})
public class VerifyOtpController_24162133 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final UserService_24162133 userService = new UserServiceImpl_24162133();
    private final OtpService_24162133 otpService = new OtpServiceImpl_24162133();

    // ===== HIỂN THỊ TRANG VERIFY OTP =====
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email = req.getParameter("email");
        req.setAttribute("email", email);

        req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
    }

    // ===== XỬ LÝ VERIFY HOẶC RESEND =====
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        String email = req.getParameter("email");

        // ===== GỬI LẠI OTP =====
        if ("resend".equals(action)) {
            try {
                otpService.sendRegisterOtp(email);
                req.setAttribute("success", "Đã gửi lại OTP. Vui lòng kiểm tra email.");
            } catch (Exception e) {
                req.setAttribute("error", "Không thể gửi lại OTP: " + e.getMessage());
            }
            req.setAttribute("email", email);
            req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
            return;
        }

        // ===== XÁC NHẬN OTP =====
        String otp = req.getParameter("otp");

        if (email == null || email.trim().isEmpty()) {
            req.setAttribute("error", "Email không hợp lệ");
            req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
            return;
        }

        if (otp == null || otp.trim().length() != 6) {
            req.setAttribute("error", "OTP phải có 6 chữ số");
            req.setAttribute("email", email);
            req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
            return;
        }

        // Verify
        boolean ok = false;
        try {
            ok = otpService.verifyRegisterOtp(email.trim(), otp.trim());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!ok) {
            req.setAttribute("error", "OTP không đúng, đã hết hạn hoặc quá số lần thử");
            req.setAttribute("email", email);
            req.getRequestDispatcher("/views/verify-otp.jsp").forward(req, resp);
            return;
        }

        // ===== KÍCH HOẠT USER =====
        User_24162133 user = userService.findByEmail(email.trim());
        if (user != null) {
            user.setLastLogin(LocalDateTime.now());
            userService.update(user);
        }

        // ===== REDIRECT SANG LOGIN VỚI THÔNG BÁO THÀNH CÔNG =====
        resp.sendRedirect(req.getContextPath() + "/login?verified=true");
    }
}