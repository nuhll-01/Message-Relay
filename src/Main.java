// Creator of Project: ToastyyX

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // TODO: Add Recipients Email Address + Verify the behavior of the code
        // TODO: Add Senders Email Address + Verify the behavior of the code

        Scanner stdIn = new Scanner(System.in);
        GmailEmailSender mailer = new GmailEmailSender();

        String sender;
        String recipient;
        String senderEmailPassword = ""; // Create a Google App Password

        System.out.print("From: ");
        sender = stdIn.next();
        if (mailer.checkLength(sender)) {
            System.out.println("Input must be between 6 - 254 characters.");
        }

        System.out.print("To: ");
        recipient = stdIn.next();
        if (mailer.checkLength(recipient)) {
            System.out.println("Input must be between 6 - 254 characters.");
        }

        // Create system properties
        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Creates a mail server
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, senderEmailPassword);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("JavaMail Message");
            message.setSentDate(new Date());
            message.addHeader("Header", "Generated Email");
            message.setText("This email message was sent using the JavaMail API!");
            Transport.send(message);
            System.out.println("Mail successfully sent");
        } catch (MessagingException messagingException) {
            System.out.println("send failed, exception: " + messagingException);
        }
        stdIn.close();
    }
}