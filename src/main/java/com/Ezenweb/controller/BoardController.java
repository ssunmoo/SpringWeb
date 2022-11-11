package com.Ezenweb.controller;

import com.Ezenweb.domain.dto.BoardDto;
import com.Ezenweb.service.BoardService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController             // 해당 클래스가 컨트롤 목적으로 사용 [ 스프링 MVC 관리 ]
@RequestMapping("/board")   // 해당 클래스의 공통 URL
public class BoardController {

    // -------------------------- page load URL --------------------------
    
    // 1. 게시물 목록 페이지[ HTML ] 열기
    @GetMapping("/list") // URL 정의
    public Resource boardlist(){
        return new ClassPathResource("templates/board/list.html");
    }

    // 2. 게시물 작성 페이지 열기
    @GetMapping("/write")
    public Resource write(){
        // Resource : 자료 [ HTML , CSS, JS 파일 등 ]
        return new ClassPathResource("templates/board/write.html");
                                    //  HTML의 파일 경로 최상위 : resources
    }

    // -------------------------------------------------------------------




    // ------------ 기능 처리 ------------

    // 1. 게시물 작성 [ 첨부파일 ]
    @PostMapping("/setboard")
    public boolean setboard(@RequestBody BoardDto boardDto ){

        // 1. Dto 내용 확인
        System.out.println(boardDto.toString());
        // 2. 서비스[비지니스 로직]로 이동
        boolean result = new BoardService().setboard(boardDto);

        // 3. 반환
        return true;
        // boolean : Content-Type [반환타입] : application/json
        // String : Content-Type [반환타입] : text/html; charset=UTF-8
        // Resource : Content-Type [반환타입] : application/json

    }

    // 2. 게시물 리스트 [ 페이지, 검색 ]
    @GetMapping("/getboards")
    public ArrayList< BoardDto > getboards() {
        // 1. 서비스로 이동
        ArrayList< BoardDto > list = new BoardService().getboards();


        // 2. 반환
        return list;
    }

//    // 3. 게시물 개별 조회
//    @GetMapping("/getboard")
//
//
//    // 4. 게시물 수정
//    @PutMapping("/updateboard")
//
//
//    // 5. 게시물 삭제
//    @DeleteMapping("/deleteboard")

       
    
    
}
