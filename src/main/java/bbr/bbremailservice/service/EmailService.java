package bbr.bbremailservice.service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import bbr.bbremailservice.model.EmailDTO;
import bbr.bbremailservice.util.Response;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final String FROM_EMAIL = "andreipetrascu7@gmail.com";
    private static final String PASSWORD = "vjnlobywbhlnysvl";

    public Response sendEmail(EmailDTO emailDTO) {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
            }
        });

        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setFrom(new InternetAddress(FROM_EMAIL));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailDTO.getToEmail()));
            mimeMessage.setSubject(emailDTO.getSubject());
            mimeMessage.setText(emailDTO.getMessage());
            Transport.send(mimeMessage);
        } catch (Exception e) {
            //e.printStackTrace();
            return new Response(0, "[sendEmail] error: " + e.toString(), null);
        }

        return new Response(1, "[sendEmail] success: Email sent successfully", null);
    }


}
