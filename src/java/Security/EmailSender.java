/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Security;

import Logger.FileLogger;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author matej_000
 */
public class EmailSender {
    
    private String server = null;
    private String userName = null;
    private String password = null;
    private final String system = System.getProperty("os.name");

    public EmailSender(String smtpHost, String userName, String password){
        this.server = smtpHost;
        this.userName = userName;
        this.password = password;
    }
    
    public void sendUserAuthEmail(String email, String userToken){
        Properties props = new Properties();
        props.put("mail.smtp.host", server);
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("GPSWebAppServer@no-reply.sk"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("Confirmation email from GPSWebApp server!!!");
            //message.setText(userToken);
            message.setSubject("Confirmation email from GPSWebApp server!!!");
            if(system.startsWith("Windows")){
                message.setContent("<h1>Hello, please confirm your email by clicking on link ...</h1><a href=http://localhost:8084/GPSWebApp/TryToAcceptUser.jsp?token=" + userToken + "&email=" + email + ">LINK</a>","text/html");
            }else{
                message.setContent("<h1>Hello, please confirm your email by clicking on link ...</h1><a href=http://gps.kpi.fei.tuke.sk/GPSWebApp/TryToAcceptUser.jsp?token=" + userToken + "&email=" + email + ">LINK</a>","text/html");
            }

            Transport.send(message);

            FileLogger.getInstance().createNewLog("Successfuly sent email to user " + email + ".");

        } catch (MessagingException e) {
            FileLogger.getInstance().createNewLog("ERROR: cannot sent email to user " + email + ".");
        }
        
    }
    
    public String getNewUserToken(){
        return RandomStringUtils.randomAlphanumeric(32);
    }

}
