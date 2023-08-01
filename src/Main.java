// Creator of Project: ToastyyX

import org.jetbrains.annotations.NotNull;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner stdIn = new Scanner(System.in);

        String sender;
        String recipient;
        String appPassword = ""; // Generate an Application Password

        // *** Please read the comment below regarding the 'appPassword' variable. ***

        // Regarding the 'appPassword' variable, when using the Gmail SMTP server,
        // the sender's email password is required.
        // When inputting the password, in some cases it will fail and throw you an Authentication exception.
        // One way to avoid this problem is by generating an 'app password' provided by Google.
        // According to Google, "an 'app password' is a 16-digit passcode
        // that gives an app or device permission to access your Google account."
        // Here is a link to the Google support page on how to generate an app password:
        // https://support.google.com/mail/answer/185833?hl=en

        System.out.print("From: "); 
        sender = stdIn.next();
        if (checkLength(sender)) {
            System.out.println("Input must be between 6 - 254 characters.");
        }

        System.out.print("To: ");
        recipient = stdIn.next();
        if (checkLength(recipient)) {
            System.out.println("Input must be between 6 - 254 characters.");
        }

        // Create system properties for the mail server
        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "true"); // Enables authentication
        properties.put("mail.smtp.starttls.enable", "true"); // Enables the use of STARTTLS
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Creates a mail server
        properties.put("mail.smtp.port", "587");

        // Create a session with account credentials
        Session session = Session.getInstance(properties, new Authenticator() { // Authenticates the sender's email account
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, appPassword);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            MimeMultipart multipart = new MimeMultipart("related");
            String messageContent = "Hey! This email message was sent by me using the JavaMail API! " +
                    "There's an attachment file containing an image of a cute cat.";

            MimeBodyPart messageBodyPart = new MimeBodyPart(); // Create a new body part that contains a message.
            messageBodyPart.setContent(messageContent, "text/html"); // Sets the content of the message body part to HTML.

            MimeBodyPart attachmentBodyPart = new MimeBodyPart(); // Creates a new body part that contains an attachment file.
            String filePath = "C:/Example/Example/Example/CuteCatImage.jpg"; // Insert a valid file path
            attachmentBodyPart.attachFile(filePath);

            message.setFrom(new InternetAddress(sender));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("JavaMail API Test Message");
            message.setSentDate(new Date());
            multipart.addBodyPart(messageBodyPart); // Adds the message body part to the multipart object.
            multipart.addBodyPart(attachmentBodyPart); // Adds the attachment body part to the multipart object.
            message.setContent(multipart);

            Transport.send(message); // Sends the message by the Transport class.
            System.out.println("\nMail successfully sent");
        } catch (MessagingException messagingException) {
            System.out.println("send failed, exception: " + messagingException);
        } catch (IOException ex) {
            throw new RuntimeException();
        }
        stdIn.close();
    }

    public static boolean checkLength(@NotNull String character) {
        final int MIN_CHARACTER_LENGTH = 6;
        final int MAX_CHARACTER_LENGTH = 254;
        return character.length() < MIN_CHARACTER_LENGTH || character.length() > MAX_CHARACTER_LENGTH;
    }
}