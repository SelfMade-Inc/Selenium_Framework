package utilities.connectivity;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;


public class EmailConfig {

    boolean debug = false;
    Properties props = new Properties ();

    public void InitMail(String mailServer, String from_addr, @org.jetbrains.annotations.NotNull String[] to_addr, String subject_body, String message_body) throws MessagingException, AddressException {

        //SMTP Server config
        props.put ("mail.smtp.starttls.enable", "true");
        props.put ("mail.smtp.EnableSSL.enable", "true");
        props.put ("mail.smtp.auth", "true");
        props.put ("mail.smtp.host", mailServer);
        props.put ("mail.debug", "true");

        //Establishing SMTP locally
        props.setProperty ("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty ("mail.smtp.socketFactory.fallback", "false");
        props.setProperty ("mail.smtp.port", "465");
        props.setProperty ("mail.smtp.socketFactory.port", "465");


        Authenticator auth = new SMTPAuthenticator ();
        Session session = Session.getDefaultInstance (props, auth);
        session.setDebug (debug);

        try {
            //Connecting to the SMTP server
            Transport bus = session.getTransport ("smtp");
            bus.connect ();
            Message message = new MimeMessage (session);

            //X-Priority values are generally numbers like 1 (for highest priority), 3 (normal) and 5 (lowest).
            message.addHeader ("X-Priority", "1");

            //reading from config for Sender email address
            message.setFrom (new InternetAddress (from_addr));

            //reading from Receiver email address list and inserting in Email To
            InternetAddress[] addressTo = new InternetAddress[to_addr.length];
            for (int i = 0 ;i < to_addr.length ;i++)
                addressTo[i] = new InternetAddress (to_addr[i]);

            message.setRecipients (Message.RecipientType.TO, addressTo);
            message.setSubject (subject_body);

            //Email body layout
            BodyPart body = new MimeBodyPart ();

            // body.setText(messageBody);
            body.setContent (message_body, "text/html");

            //BodyPart attachment = new MimeBodyPart();
            //DataSource source = new FileDataSource(attachmentPath);
            //attachment.setDataHandler(new DataHandler(source));
            //attachment.setFileName(attachmentName);
            MimeMultipart multipart = new MimeMultipart ();
            multipart.addBodyPart (body);
            //multipart.addBodyPart(attachment);
            message.setContent (multipart);

            //Send message
            Transport.send (message);
            System.out.println ("Sucessfully Sent mail to All Users");
            bus.close ();

        } catch (MessagingException mex) {
            mex.printStackTrace ();
        }
    }

    private class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthenticated() {
            String username = ConnectivityConfig.from_addr;
            String password = ConnectivityConfig.password;
            return new PasswordAuthentication (username, password);
        }
    }

    //To send a generic email consisting of Log prints
    /*public void GenericEmail()
    {
        // email ID of Recipient.
        String recipient = "recipient@gmail.com";

        // email ID of Sender.
        String sender = "sender@gmail.com";

        // using host as localhost
        String host = "127.0.0.1";

        // Getting system properties
        props.getProperties();

        // Setting up mail server
        props.setProperty("mail.smtp.host", host);

        // creating session object to get properties
        Session session = Session.getDefaultInstance(properties);

        try
        {
            // MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From Field: adding senders email to from field.
            message.setFrom(new InternetAddress(sender));

            // Set To Field: adding recipient's email to from field.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

            // Set Subject: subject of the email
            message.setSubject("This is Suject");

            // set body of the email.
            message.setText("This is a test mail");

            // Send email.
            Transport.send(message);
            System.out.println("Mail successfully sent");
        }
        catch (MessagingException mex)
        {
            mex.printStackTrace();
        }
    }*/

    //To send a HTMl formatted email consisting of Log prints
    /*public void HTMLEmail(){

        // Recipient's email ID needs to be mentioned.
        String to = "abcd@gmail.com";

        // Sender's email ID needs to be mentioned
        String from = "web@gmail.com";

        // Assuming you are sending email from localhost
        String host = "localhost";

        // Get system properties
        props.getProperties();

        // Setup mail server
        props.setProperty("mail.smtp.host", host);

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("This is the Subject Line!");

            // Send the actual HTML message, as big as you like
            message.setContent("<h1>This is actual message</h1>", "text/html");

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }*/

    //To send a Generic email with Log attachements
    /*public void EmailAttachment(){
        // Recipient's email ID needs to be mentioned.
        String to = "abcd@gmail.com";

        // Sender's email ID needs to be mentioned
        String from = "web@gmail.com";

        // Assuming you are sending email from localhost
        String host = "localhost";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("This is the Subject Line!");

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart ();

            // Fill the message
            messageBodyPart.setText("This is message body");

            // Create a multipar message
            Multipart multipart = new MimeMultipart ();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String filename = "file.txt";
            DataSource source = new FileDataSource (filename);
            messageBodyPart.setDataHandler(new DataHandler (source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart );

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }*/

    //To Generate Email copy the code to any method
    /*public void GenerateEmail(){

        //To send a mail
        EmailConfig mailGenerator = new EmailConfig ();
        mailGenerator.InitMail (ConnectivityConfig.server, ConnectivityConfig.from_addr, ConnectivityConfig.to_addr, ConnectivityConfig.subject_body,ConnectivityConfig.message_body,ConnectivityConfig.attachment_path,ConnectivityConfig.attachment_name);

    }*/
}
