package sokpheng.com.blogapi.model.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import sokpheng.com.blogapi.exception.SokphengNotFoundException;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    @Value("${url.base-url}")
    private String verifyUrl;
    public void sendVerificationEmail(String to, String token) {

        String subject = "Verify Your Email – Daily Write - Blog Platform";
        String verificationUrl = verifyUrl + "/api/v100/auth/verify-email?token=" + token;

        String content = """
        <div style="font-family: Arial, sans-serif; background-color: #f4f6f8; padding: 40px 0;">
            <div style="max-width: 500px; margin: auto; background: #ffffff; 
                        padding: 30px; border-radius: 8px; text-align: center; 
                        box-shadow: 0 2px 8px rgba(0,0,0,0.05);">

                <h2 style="color: #222; margin-bottom: 10px;">Daily Write</h2>
                
                <p style="color: #555; font-size: 15px;">
                    Thank you for registering.
                </p>

                <p style="color: #555; font-size: 15px;">
                    Please verify your email address to activate your account, verification will be expired in 15 Minutes.
                </p>

                <a href="%s" 
                   style="display: inline-block; margin-top: 20px; 
                          padding: 12px 25px; font-size: 14px; 
                          color: #ffffff; background-color: #2563eb; 
                          text-decoration: none; border-radius: 5px;">
                    Verify Email
                </a>

                <p style="margin-top: 30px; font-size: 12px; color: #999;">
                    If you did not create an account, you can safely ignore this email.
                </p>

            </div>
        </div>
        """.formatted(verificationUrl);
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true); // true = HTML
            mailSender.send(message);
        }catch (MessagingException messagingException){
            throw new SokphengNotFoundException("Error during sending email for verify");
        }
    }
}
