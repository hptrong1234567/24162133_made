package vn.iotstar.service;

public interface OtpService_24162133 {
    /** Tạo OTP, hash, lưu DB và gửi email */
    void sendRegisterOtp(String email);
    /** Verify OTP đăng ký */
    boolean verifyRegisterOtp(String email, String otp);
}