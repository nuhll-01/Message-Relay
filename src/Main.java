import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.nio.*;

public class Main {
    public static void main(String[] args) {
        Scanner stdIn = new Scanner(System.in);

        int minCharacterLength = 6;
        int maxCharacterLength = 254;
        String recipient;
        String senderEmail;
        String senderEmailPassword = ""; // Google Password

        // TODO: Add Recipients Email Address
        System.out.print("Enter the recipient email address: ");
        recipient = stdIn.nextLine();
        if (recipient.length() < minCharacterLength || recipient.length() > maxCharacterLength) {
            System.out.println("Input must be between 6 - 254 characters.");
        }

        // TODO: Add Senders Emails Address
        System.out.print("Enter the sender email address: ");
        senderEmail = stdIn.nextLine();
        if (senderEmail.length() < minCharacterLength || senderEmail.length() > maxCharacterLength) {
            System.out.println("Input must be between 6 - 254 characters.");
        }

        stdIn.close();

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