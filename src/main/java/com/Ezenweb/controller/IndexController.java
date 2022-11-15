package com.Ezenweb.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    // 0. 인덱스 페이지 열기
    @GetMapping("/")
    public Resource getindex() {
        return new ClassPathResource("templates/index.html");
        // 프로젝트내 resource -> templates -> member -> signup.html 반환
    }





}
