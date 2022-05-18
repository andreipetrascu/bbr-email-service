package bbr.bbremailservice.service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.Properties;

import bbr.bbremailservice.model.EmailDTO;
import bbr.bbremailservice.util.Response;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {

    private static final String FROM_EMAIL = "andreipetrascu7@gmail.com";
    private static final String ADMIN_EMAIL = "cw31577@gmail.com";
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

    public Response sendSecurityCode(EmailDTO emailDTO) {

        if (emailDTO.getToEmail().equals(ADMIN_EMAIL)) {
            String securityCode = generateSecurityCode();
            writeToCsv(securityCode);
            emailDTO.setSubject("Security Code");
            emailDTO.setMessage("Your security code is: " + securityCode);
            return sendEmail(emailDTO);
        } else {
            return new Response(0, "Email not found", null);
        }
    }

    public Response verifySecurityCode(EmailDTO emailDTO) {

        String securityCode = getSecurityCodeFromCsv();

        if (securityCode.equals(emailDTO.getMessage())) {
            return new Response(1, "[verifySecurityCode] success", null);
        } else {
            return new Response(0, "[verifySecurityCode] error: wrong security code", null);
        }
    }

    private String getSecurityCodeFromCsv() {
        String file = "security.csv";
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            if ((line = br.readLine()) != null) {
                System.out.println("...");
                System.out.println(line);
                return line;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return "";
    }

    private void writeToCsv(String securityCode) {
        try (PrintWriter writer = new PrintWriter("security.csv")) {

            StringBuilder sb = new StringBuilder();
            sb.append(securityCode);
            writer.write(sb.toString());

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private String generateSecurityCode() {
        Random random = new Random();
        return String.valueOf((1000 + random.nextInt(100000)) % 10000);
    }

}
