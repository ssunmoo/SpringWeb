package com.Ezenweb.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfigration extends WebSecurityConfigurerAdapter {

    @Override // 재정의 : 상속 받은 클래스로부터 메소드 구현
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http); // 주석처리하면 인증 다 풀림
    }


}
