package components;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMail {
    public static void getSendEmail(String toUserMessage, String email) {
        // Recipient's email ID needs to be mentioned.
        String to = email;

        // Sender's email ID needs to be mentioned
        String from = "aaadmain78@gmail.com";

        // Assuming you are sending email through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server (using port 587 for TLS encryption)
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");  // Enable TLS for secure connection
        properties.put("mail.smtp.auth", "true");           // Authentication required

        // Get the Session object (with username and password)
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                // Use an app password generated from your Google Account settings
                return new PasswordAuthentication("aaadmain78@gmail.com", "vxsckjrirjzqnrvx");
            }

        });

        // Used to debug SMTP issues
        //session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Your Account Status!!");

            // Now set the actual message
            message.setText("Your request to create a business account on the Event Planner System : \n " + toUserMessage);

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}