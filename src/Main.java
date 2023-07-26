// Creator of Project: ToastyyX

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.nio.*;

public class Main {
    static final int MIN_CHARACTER_LENGTH = 6;
    static final int MAX_CHARACTER_LENGTH = 254;
    static Scanner stdIn = new Scanner(System.in);

    // TODO: Add Recipients Email Address + Verify the behavior of the code
    public static String getRecipient() {
        String recipient;
        System.out.print("Enter the recipient email address: ");
        recipient = stdIn.nextLine();
        if (recipient.length() < MIN_CHARACTER_LENGTH || recipient.length() > MAX_CHARACTER_LENGTH) {
            System.out.println("Input must be between 6 - 254 characters.");
        }
        stdIn.close();
        return recipient;
    }

    // TODO: Add Senders Email Address + Verify the behavior of the code
    public static String getSender() {
        String sender;
        System.out.print("Enter the sender email address: ");
        sender = stdIn.nextLine();
        if (sender.length() < MIN_CHARACTER_LENGTH || sender.length() > MAX_CHARACTER_LENGTH) {
            System.out.println("Input must be between 6 - 254 characters.");
        }
        stdIn.close();
        return sender;
    }

    public static void main(String[] args) {
        String senderEmailPassword = ""; // Google Application Password

        // TODO: Create Properties Object
        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderEmailPassword);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
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
    }
}