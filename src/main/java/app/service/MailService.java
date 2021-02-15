package app.service;

import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailService {

    public String sendEmail(String mail, String msg) {
        String from = "sex-1994@mail.ru";
        String pass = "$uoThAs31aRO";
        String[] to = { mail }; // list of recipient email addresses
        String subject = "Java send mail example";
        String body = msg;

        Properties props = System.getProperties();
        String host = "smtp.mail.ru";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException ae) {
            ae.printStackTrace();
        }
        return "ok";
    }

    public String sendGreetingEmail(String mail) {
        return sendEmail(mail, "Glad to see you " + mail  + ". Welcome to out site!");
    }

    public String sendEmailOnUpdating(String mail) {
        return sendEmail(mail, "Your profile has been updated!");
    }

    public String sendEmailOnDeletion(String mail) {
        return sendEmail(mail, "It is sad that you go off so fast, " + mail  + ". Goodbye!");
    }
}
