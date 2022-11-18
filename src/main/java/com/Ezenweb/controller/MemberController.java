package com.Ezenweb.controller;

import com.Ezenweb.domain.dto.MemberDto;
import com.Ezenweb.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // 해당 클래스가 컨트롤임을 명시 [ RestFul api 사용 ]
@RequestMapping("/member") // 공통 URL 매핑 주소
public class MemberController {
    
    // -------------------------------- 전역 객체 -------------------------------------
    @Autowired // 제어 역전 [DI] 스프링 컨테이너 빈 생성 [ 외부에 메모리 위임 ]
    private MemberService memberService;

    // -------------------------------- HTML 반환 매핑 --------------------------------

    // 1. 회원가입 페이지 열기
    @GetMapping("/signup")
    public Resource getsignup() {
        return new ClassPathResource("templates/member/signup.html");
        // 프로젝트내 resource -> templates -> member -> signup.html 반환
    }

    // 2. 로그인 페이지 열기
    @GetMapping("/login")
    public Resource getlogin() {
        return new ClassPathResource("templates/member/login.html");
    }

    // 3. 비밀번호 페이지 열기
    @GetMapping("/findpw")
    public Resource findpw( ){
        return new ClassPathResource("templates/member/findpw.html");
    }

    // 4. 회원 탈퇴 페이지 열기
    @GetMapping("/delete")
    public Resource delete() {
        return new ClassPathResource("templates/member/delete.html");
    }

    // 5. 비밀 번호 변경
    @GetMapping("/update")
    public Resource updatepw() {
        return new ClassPathResource("templates/member/update.html");
    }


    // -------------------------------- 서비스/기능 매핑 ------------------------------------

    // 1. 회원가입 기능
    @PostMapping("/setmember")
    public int setmember( @RequestBody MemberDto memberDto  ){
        // 1. 서비스 메소드 호출
       int result = memberService.setmember( memberDto );
        // 2. 반환
        return result;
    }

    // 2. 로그인 기능
    @PostMapping("/getmember")
    public int getmember( @RequestBody MemberDto memberDto ){
        int result = memberService.getmember( memberDto );
        return result;
    }

    // 3. 비밀번호 찾기 기능
    @GetMapping("/getpw")
    public String getpw( @RequestParam("memail") String memail ) {
        String result = memberService.getpw( memail );
        return result;
    }

    // 4. 회원 탈퇴
    @DeleteMapping("/setdelete")
    public int setdelete( @RequestParam("mpw") String mpw ){
        int result = memberService.setdelete( mpw );
        return result;
    }

    // 5. 비밀번호 변경
    @PutMapping("/setupdate")
    public int setupdate( @RequestParam("mpw") String mpw ){
        int result = memberService.setupdate( mpw );
        return result;
    }

    // 6. 로그인 확인
    @GetMapping("/getloginMno")
    public int getloginMno(){
        int result = memberService.getloginMno();
        return result;
    }

    // 7. 로그아웃
    @GetMapping("/logout") // 7. 로그아웃
    public void logout(){
        memberService.logout();
    }

    // 8. 회원목록
    @GetMapping("/list") // 8. 회원 목록
    @ResponseBody
    public List<MemberDto> list(){
        List<MemberDto> list = memberService.list();
        System.out.println("확인:" + list );
        return list;
    }

    // 9. 메일 인증코드 발송
    @GetMapping("/getauth")
    public String getauth( @RequestParam("toemail") String toemail ) {
        return memberService.getauth( toemail );
    }






}
