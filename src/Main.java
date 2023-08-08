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
        String userResponse;
        String finalSender;
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
        sender = stdIn.nextLine();
        System.out.print("\nVerify your email" + "\nIs this correct? " + sender + " (Yes/No): ");
        userResponse = stdIn.nextLine();
        while (isInvalidEmail(userResponse)) {
            System.out.print("From: ");
            sender = stdIn.nextLine();
            System.out.print("\nVerify Email" + "\nIs this correct? " + sender + " (Yes/No): ");
            userResponse = stdIn.nextLine();
        }

        if (checkLength(sender)) {
            System.out.println("Input must be between 6 - 254 characters.");
        }

        finalSender = sender;

        System.out.print("To: ");
        recipient = stdIn.nextLine();
        System.out.print("\nVerify Email" + "\nIs this correct? " + recipient + " (Yes/No): ");
        userResponse = stdIn.nextLine();
        while (isInvalidEmail(userResponse)) {
            System.out.print("To: ");
            recipient = stdIn.nextLine();
            System.out.print("\nVerify Email" + "\nIs this correct? " + recipient + " (Yes/No): ");
            userResponse = stdIn.nextLine();
        }

        if (checkLength(recipient)) {
            System.out.println("Input must be between 6 - 254 characters.");
        }

        // Create system properties for the mail server
        Properties properties = System.getProperties();
        properties.put("mail.smtp.auth", "true"); // Enables authentication
        properties.put("mail.smtp.starttls.enable", "true"); // Enables the use of STARTTLS
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Creates a mail server
        properties.put("mail.smtp.port", "587"); // Sets the port of the mail server to 587 (recommended)

        // Create a session with account credentials
        Session session = Session.getInstance(properties, new Authenticator() { // Authenticates the sender's email account
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(finalSender, appPassword);
            }
        });

        // For additional information regarding the 'MimeMultiPart' parameter subtypes
        // Reference the following article:
        // https://en.wikipedia.org/wiki/MIME#:~:text=at%20the%20end.-,Multipart%20subtypes,field%20of%20the%20overall%20message.
        try {
            MimeMessage message = new MimeMessage(session);

            String messageContent = "Hey! This email message was sent by me using the <strong>JavaMail API!</strong> " +
                    "There's an attachment file containing an image of a cute cat.";
            MimeBodyPart messageBodyPart = new MimeBodyPart(); // Create a new body part that contains a message.
            messageBodyPart.setContent(messageContent, "text/html"); // Sets the content of the message body part to HTML.

            MimeBodyPart attachmentBodyPart = new MimeBodyPart(); // Creates a new body part that contains an attachment file.
            String filePath = "C:/Example/Example/Example/CuteCatImage.jpg"; // Insert a valid file path
            attachmentBodyPart.attachFile(filePath); // Attaches the file to the body part.

            String secondMessageContent = "<br><br>While you're add it, " +
                    "I highly recommend checking out this song by Cocteau Twins called " +
                    "<a href=\"https://youtu.be/PV2bO40zL0I\">'She Will Destroy You'</a>.";
            MimeBodyPart messageBodyPart2 = new MimeBodyPart(); // Create a new body part that contains a message.
            messageBodyPart2.setContent(secondMessageContent, "text/html"); // Sets the content of the message body part to HTML.

            MimeMultipart multipart = new MimeMultipart("mixed"); // Creates a new multipart object w/ subtype 'mixed.'

            message.setFrom(new InternetAddress(sender));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSentDate(new Date());
            message.setSubject("JavaMail API Test Message");
            multipart.addBodyPart(messageBodyPart); // Adds the message body part to the multipart object.
            multipart.addBodyPart(attachmentBodyPart); // Adds the attachment body part to the multipart object.
            multipart.addBodyPart(messageBodyPart2); // Adds the second message body part to the multipart object.
            message.setContent(multipart);

            Transport.send(message); // Sends the message by the Transport class.
            System.out.println("\nMail Successfully Sent");
        } catch (MessagingException messagingException) {
            System.out.println("\nSend Failed: Exception: " + messagingException);
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

    public static boolean isInvalidEmail(@NotNull String response) {
        if (response.equalsIgnoreCase("no")) {
            return true;
        } else if (!response.equalsIgnoreCase("no") && !response.equalsIgnoreCase("yes") || response.contains(" ")) {
            System.out.println("Invalid Input.");
            return true;
        }
        return false;
    }

    public static boolean isValidYesNoResponse(@NotNull String response) {
        if (response.equalsIgnoreCase("no")) {
            return true;
        } else if (!response.equalsIgnoreCase("no") && !response.equalsIgnoreCase("yes") || response.contains(" ")) {
            System.out.println("Invalid Input.");
            return true;
        }
        return false;
    }

    // TODO:
    //  Implement a method that allows the user to input a path of a file
    //  (i.e. image, audio) as part of the email attachment.

    public static void setAttachment(String response) {
        Scanner stdIn = new Scanner(System.in);
        String attachment;
        String attachmentResponse;

        System.out.print("Insert Attachment? " + " (Yes/No): ");
        attachmentResponse = stdIn.nextLine();
        while (isValidYesNoResponse(attachmentResponse)) {
            System.out.print("Insert Attachment? " + " (Yes/No): ");
            attachmentResponse = stdIn.nextLine();
        }
    }

    public static String getAttachment(String attachment) {
        return attachment;
    }
}