package com.ptit.librarymanagement.common.mail;


import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class MailService {
    private static MailService mailService;
    private final String username = "ngocuyenle18@gmail.com";
    private final String password = "bexi hwqq egbi yfzk";
    private final Session session;
    static {
        mailService = new MailService();
    }

    private MailService () {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

    }


    public void sendMail (String destinationAddress, String mailTitle ,String mailBody) {

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("ngocuyenle18@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(destinationAddress)
            );
            message.setSubject(mailTitle);
            message.setText(mailBody);

            Transport.send(message);
            System.out.println("Gửi mail thành công!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
    public static MailService getMailService () {
        return mailService;
    }

}
