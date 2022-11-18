package com.Ezenweb;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // 일자 자동으로 넣어주는 어노테이션
public class Webstart {

    public static void main(String[] args) {     // 메인 스레드
        SpringApplication.run( Webstart.class ); // 스프링 어플리케이션 실행

    }
}
