package com.Ezenweb.controller;

import com.Ezenweb.domain.dto.MemberDto;
import com.Ezenweb.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController // 해당 클래스가 컨트롤임을 명시 [ RestFul api 사용 ]
public class MemberController {

    @Autowired // 제어 역전 [DI] 스프링 컨테이너 빈 생성 [ 외부에 메모리 위임 ]
    private MemberService memberService;

    @GetMapping("/setmember") // RestFul api
    public int setmember( ) {

        // 1. 테스트용 dto 생성
        MemberDto memberDto =  MemberDto.builder()
                                .memail("ggg@naver.com")
                                .mpw("12341234")
                                .build();
        // 1. 서비스 메소드 호출
       int result = memberService.setmember( memberDto );
        
        // 2. 반환
        return result;
    }


}
