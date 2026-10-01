package vn.iotstar.service.impl;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

import vn.iotstar.dao.OtpDAO_24162133;
import vn.iotstar.dao.impl.OtpDAOImpl_24162133;
import vn.iotstar.model.OtpToken_24162133;
import vn.iotstar.service.EmailService_24162133;
import vn.iotstar.service.OtpService_24162133;
import vn.iotstar.util.Constant_24162133;

public class OtpServiceImpl_24162133 implements OtpService_24162133 {

    private final OtpDAO_24162133 otpDAO = new OtpDAOImpl_24162133();
    private final EmailService_24162133 emailService = new EmailServiceImpl_24162133();
    private final SecureRandom random = new SecureRandom();

    private String generateOtp() {
        return String.format("%06d", random.nextInt(1_000_000));
    }

    @Override
    public void sendRegisterOtp(String email) {
        // Xóa OTP cũ
        otpDAO.deleteByEmailAndType(email, "REGISTER");

        // Tạo OTP mới (không hash — giữ đơn giản, đề không yêu cầu BCrypt OTP)
        String otp = generateOtp();

        OtpToken_24162133 token = new OtpToken_24162133();
        token.setEmail(email);
        token.setOtpHash(otp);   // Lưu thẳng OTP
        token.setType("REGISTER");
        token.setExpiresAt(LocalDateTime.now().plusMinutes(Constant_24162133.OTP_MINUTES));
        token.setAttempts(0);
        token.setUsed(false);
        token.setCreatedAt(LocalDateTime.now());

        otpDAO.insert(token);

        // Gửi email
        emailService.sendOtp(email, otp, "BookStore - Xác nhận đăng ký tài khoản");
    }

    @Override
    public boolean verifyRegisterOtp(String email, String otp) {
        Optional<OtpToken_24162133> opt = otpDAO.findLatestByEmailAndType(email, "REGISTER");
        if (opt.isEmpty()) return false;

        OtpToken_24162133 token = opt.get();

        // Kiểm tra hết hạn
        if (token.getExpiresAt().isBefore(LocalDateTime.now())) return false;

        // Kiểm tra số lần thử
        if (token.getAttempts() >= Constant_24162133.OTP_MAX_ATTEMPTS) return false;

        // Tăng attempts
        token.setAttempts(token.getAttempts() + 1);

        // So sánh OTP
        if (!otp.equals(token.getOtpHash())) {
            otpDAO.update(token);
            return false;
        }

        // Đúng → đánh dấu đã dùng
        token.setUsed(true);
        otpDAO.update(token);
        return true;
    }
}