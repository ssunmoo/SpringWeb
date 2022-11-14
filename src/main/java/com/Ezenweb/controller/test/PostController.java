package com.Ezenweb.controller.test;

import com.Ezenweb.domain.dto.MemberDto;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController // 스프링이 해당 클래스가 RestController 임을 알도록 설정 [ 빈 등록 ]
@RequestMapping("/api/v1/post-api") // 클래스 URL
public class PostController {

    // 1. p.68
    @RequestMapping(value = "/domain", method = RequestMethod.POST)
    public String postExample(){
        return "Hello Post API";
    }

    // 2. p.69
    @PostMapping("/member")
    public String postMember( @RequestBody Map< String, Object > postData ){
        return postData.toString();
    }

    @PostMapping("/member2")
    public String postMemberDto( @RequestBody MemberDto memberDto ){
        return memberDto.toString();
    }


}
