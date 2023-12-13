package com.mxtec.mail.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfiguration {

  @Value("${mail.mailer}")
  private String mailMailer;

  @Value("${mail.host}")
  private String mailHost;

  @Value("${mail.port}")
  private Integer mailPort;

  @Value("${mail.username}")
  private String mailUserName;

  @Value("${mail.password}")
  private String mailPassword;

  @Value("${mail.encryption}")
  private String mailEncryption;

  @Value("${mail.from.address}")
  private String mailFromAddress;

  @Value("${mail.from.name}")
  private String mailFromName;

  @Value("${mail.smtp.auth}")
  private Boolean mailAuth;

  @Value("${mail.smtp.starttls.enable}")
  private Boolean mailStarttls;

  @Value("${mail.debug}")
  private Boolean mailDebug;

  @Bean
  public JavaMailSender getJavaMailSender() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

    mailSender.setHost(mailHost);
    mailSender.setPort(mailPort);
    mailSender.setUsername(mailUserName);
    mailSender.setPassword(mailPassword);

    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", mailMailer);
    props.put("mail.smtp.auth", mailAuth);
    props.put("mail.smtp.starttls.enable", mailStarttls);
    props.put("mail.debug", mailDebug);

    return mailSender;
  }
}
