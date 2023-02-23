package org.project.NoteService.services;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.project.NoteService.user.data.user;
import org.springframework.beans.factory.annotation.Value;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class emailController {
    @Value("${email.username}")
    private String username;

    @Value("${email.password}")
    private String password;




    public void SendConfirmationMail(String token, user user)
    {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(user.getEmail())
            );
            message.setSubject("Activation account for "+user.getEmail());

            String link = getTamplate("http://localhost:3000/activation?token="+token);
            message.setContent(link, "text/html");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    public void SendResetPasswordMail(String token, user user)
    {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(user.getEmail())
            );
            message.setSubject("Reset password for "+user.getEmail());

            String link = getTamplateResetPassword("http://localhost:3000/new-password?token="+token);
            message.setContent(link, "text/html");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    private String getTamplate(String link){
        try {
            //Instantiate Configuration class
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
            cfg.setDirectoryForTemplateLoading(new File("src/main/resources"));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            //Create Data Model
            Map<String, Object> map = new HashMap<>();
            map.put("linkToActivation", link);

            //Instantiate template
            Template template = cfg.getTemplate("ActivationEmail.ftl");
            //Console output
            Writer console = new StringWriter();// now it works fine
            template.process(map, console);
            String data = console.toString();
            console.flush();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return "";
    }
    private String getTamplateResetPassword(String link){
        try {
            //Instantiate Configuration class
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
            cfg.setDirectoryForTemplateLoading(new File("src/main/resources"));
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            //Create Data Model
            Map<String, Object> map = new HashMap<>();
            map.put("linkToReset", link);

            //Instantiate template
            Template template = cfg.getTemplate("resetPassword.ftl");
            //Console output
            Writer console = new StringWriter();// now it works fine
            template.process(map, console);
            String data = console.toString();
            console.flush();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return "";
    }


}