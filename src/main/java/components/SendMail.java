package components;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.logging.Logger;

public class SendMail {
    private static final Logger logger = Logger.getLogger(SendMail.class.getName());

    private SendMail() {
        throw new IllegalStateException("Utility class");
    }

    public static void sendEmail(String toUserMessage, String email) {
        String from = "aaadmain78@gmail.com";
        String host = "smtp.gmail.com";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                // Use an app password generated from your Google Account settings
                return new PasswordAuthentication("aaadmain78@gmail.com", "vxsckjrirjzqnrvx");
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email)); // Using email directly
            message.setSubject("Your Account Status!!");
            message.setText("Your request to create a business account on the Event Planner System : \n " + toUserMessage);

            logger.info("Sending...");
            Transport.send(message);
            logger.info("Sent message successfully.");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
