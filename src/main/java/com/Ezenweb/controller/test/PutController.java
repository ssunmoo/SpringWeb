package com.Ezenweb.controller.test;

import com.Ezenweb.domain.dto.MemberDto;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/put-api")
public class PutController {

    // p.70
    @PutMapping( "/member" )
    public String putMember(@RequestBody Map< String, String > putData){
        return putData.toString();
    }

    // p.71     반환타입 : 문자열 [ String ] 
    @PutMapping( "/member1" )
    public String postMemberDto(@RequestBody MemberDto memberDto ){
        return memberDto.toString(); // 반환 타입이 String 이기 때문에 문자열로 전송
    }

    // p. 72     반환타입 : DTO 객체 [ MemberDto ]
    @PutMapping( "/member2" )
    public MemberDto postMemberDto2(@RequestBody MemberDto memberDto ){
        return memberDto; // 반환 타입이 객체여서 객체로 전송 object
    }





}
