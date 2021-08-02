package com.gzt.util;

import com.sun.mail.util.MailSSLSocketFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Properties;

public class MailUtil {


    /**
     * @param subject 邮件主题
     * @param content 邮件内容
     * @param to      发送给谁，邮箱地址
     */
    public static void sendMail(String subject, String content, String to) {
        // 通过发件人的账号和密码，连接发送邮件的服务器【登录邮箱】
        Properties prop = new Properties();
        prop.setProperty("mail.host", Const.email.MAIL_HOST);
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");
        Session session = Session.getInstance(prop);
        Transport ts = null;
        try {
            ts = session.getTransport();
            ts.connect(Const.email.MAIL_HOST, Const.email.MAIL_FROM, Const.email.MAIL_PASSWORD);
            // 写邮件
            MimeMessage message = new MimeMessage(session);
            // 发件人[当前登录人] from
            message.setFrom(new InternetAddress(Const.email.MAIL_FROM));
            // 收件人 to
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // 标题 subject
            message.setSubject(subject);
            // 内容
            message.setContent(content, "text/html;charset=UTF-8");
            // 发送按钮
            ts.sendMessage(message, message.getAllRecipients());
        } catch (MessagingException e) {
            e.printStackTrace();
        } finally {
            if (null != ts) {
                try {
                    ts.close();
                    ts = null;
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        //sendMail("我是邮件主题","我是邮件内容","1511182565@qq.com");
        //sendMailWithAttachment("", "1249094460@qq.com", "");
    }

    public static boolean sendMailWithAttachment(String content, String to, String filename) {
        final Logger logger = LoggerFactory.getLogger(MailUtil.class);
        // 获取系统属性
        Properties properties = System.getProperties();
        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", Const.email.MAIL_HOST);

        properties.put("mail.smtp.auth", "true");
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();

            sf.setTrustAllHosts(true);
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.ssl.socketFactory", sf);
            // 获取默认session对象
            Session session = Session.getInstance(properties, new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {     //qq邮箱服务器账户、第三方登录授权码
                    return new PasswordAuthentication(Const.email.MAIL_FROM, Const.email.MAIL_PASSWORD); //发件人邮件用户名、密码
                }
            });


            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(Const.email.MAIL_FROM));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: 主题文字
            message.setSubject("来自西西的消息！❤ 张芮");

            // 创建消息部分
            BodyPart messageBodyPart = new MimeBodyPart();

            // 消息
            messageBodyPart.setText(content);

            // 创建多重消息
            Multipart multipart = new MimeMultipart();

            // 设置文本消息部分
            multipart.addBodyPart(messageBodyPart);

            // 附件部分
            messageBodyPart = new MimeBodyPart();
            //设置要发送附件的文件路径
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));

            //messageBodyPart.setFileName(filename);
            //处理附件名称中文（附带文件路径）乱码问题
            messageBodyPart.setFileName(MimeUtility.encodeText(filename));
            multipart.addBodyPart(messageBodyPart);

            // 发送完整消息
            message.setContent(multipart);

            //   发送消息
            Transport.send(message);
            logger.info("Sent message successfully....");
            return true;
        } catch (Exception mex) {
            mex.printStackTrace();
            return false;
        }
    }

    public static boolean sendMailWithAttachmentToXiXi(String content, String to, String filename) {
        final Logger logger = LoggerFactory.getLogger(MailUtil.class);
        // 获取系统属性
        Properties properties = System.getProperties();
        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", Const.email.MAIL_HOST);

        properties.put("mail.smtp.auth", "true");
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();

            sf.setTrustAllHosts(true);
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.ssl.socketFactory", sf);
            // 获取默认session对象
            Session session = Session.getInstance(properties, new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {     //qq邮箱服务器账户、第三方登录授权码
                    return new PasswordAuthentication(Const.email.MAIL_FROM_RUI, Const.email.MAIL_PASSWORD_RUI); //发件人邮件用户名、密码
                }
            });


            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(Const.email.MAIL_FROM_RUI));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: 主题文字
            message.setSubject("来自芮宝的消息！❤ ");

            // 创建消息部分
            BodyPart messageBodyPart = new MimeBodyPart();

            // 消息
            messageBodyPart.setText(content);

            // 创建多重消息
            Multipart multipart = new MimeMultipart();

            // 设置文本消息部分
            multipart.addBodyPart(messageBodyPart);

            // 附件部分
            messageBodyPart = new MimeBodyPart();
            //设置要发送附件的文件路径
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));

            //messageBodyPart.setFileName(filename);
            //处理附件名称中文（附带文件路径）乱码问题
            messageBodyPart.setFileName(MimeUtility.encodeText(filename));
            multipart.addBodyPart(messageBodyPart);

            // 发送完整消息
            message.setContent(multipart);

            //   发送消息
            Transport.send(message);
            logger.info("Sent message successfully....");
            return true;
        } catch (Exception mex) {
            mex.printStackTrace();
            return false;
        }
    }

}
