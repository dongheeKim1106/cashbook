package com.gdu.cashbook1;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@SpringBootApplication
@PropertySource("classpath:google.properties")
public class CashbookApplication {
	// 구글 아이디 비밀번호 가져오기
	@Value("${google.username}")
	public String username;
	@Value("${google.password}")
	public String password;
	
	public static void main(String[] args) {
		SpringApplication.run(CashbookApplication.class, args);
	}

	@Bean // 빈 생성 
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setHost("smtp.gmail.com"); // 메일 서버 이름
		javaMailSender.setPort(587);
		// System.out.println(username);
		// System.out.println(password);
		javaMailSender.setUsername(username);
		javaMailSender.setPassword(password);
  
		Properties prop = new Properties(); // Properties == HashMap<String, String> 문자열로 되있는걸 문자열로 만든다 맵이다
		prop.setProperty("mail.smtp.auth", "true");
		prop.setProperty("mail.smtp.starttls.enable", "true");
		javaMailSender.setJavaMailProperties(prop);
		return javaMailSender;
	}
}
