package vn.iotstar.service.impl;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import vn.iotstar.service.EmailService_24162133;
import vn.iotstar.util.Constant_24162133;

public class EmailServiceImpl_24162133 implements EmailService_24162133 {

    @Override
    public void sendOtp(String toEmail, String otp, String subject) {
        // Cấu hình SMTP Gmail
        Properties props = new Properties();
        props.put("mail.smtp.host", Constant_24162133.MAIL_HOST);
        props.put("mail.smtp.port", Constant_24162133.MAIL_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Xác thực
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        Constant_24162133.MAIL_USERNAME,
                        Constant_24162133.MAIL_PASSWORD);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Constant_24162133.MAIL_USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject, "UTF-8");

            String content = "Xin chào,\n\n"
                    + "Mã OTP của bạn là: " + otp + "\n\n"
                    + "OTP có hiệu lực trong " + Constant_24162133.OTP_MINUTES + " phút.\n"
                    + "Không chia sẻ mã này cho người khác.\n\n"
                    + "Trân trọng,\nBookStore";

            message.setText(content, "UTF-8");

            Transport.send(message);

            System.out.println(">>> Đã gửi OTP đến: " + toEmail);

        } catch (Exception e) {
            System.out.println(">>> Lỗi gửi mail: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Không thể gửi email OTP", e);
        }
    }
}