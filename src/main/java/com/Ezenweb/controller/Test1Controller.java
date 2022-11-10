package com.Ezenweb.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 해당 클래스가 controller 임을 명시 [ 스프링 부트 어노테이션이 스캔함 ]
@RequestMapping("/test1") // URL 경로 정의 : http://localhost:8081/test1
public class Test1Controller {

    // 1. text.html 열기
//    @GetMapping("")    // 해당 경로 : http://localhost:8081/test1
//    public String gettest(){
//        return "test1.html";
//    }

    @GetMapping("")    // 해당 경로 : http://localhost:8081/test1
    public Resource gettest(){
        return new ClassPathResource("templates/test1.html");
    }
    // 반환타입 : 문자열 String
    // 반환타입 : HTML 템플릿 없이 HTML 반환 ( 외부적인 기능 x )
    // 반환 타입 Resource , 반환값
        // new ClassPathResource("html.html");
        // 프로젝트내 resource 폴더부터 시작
    // import org.springframework.core.io.Resource;








}
