package com.Ezenweb.controller;

import com.Ezenweb.domain.dto.BcategoryDto;
import com.Ezenweb.domain.dto.BoardDto;
import com.Ezenweb.domain.dto.GuestbookCgDto;
import com.Ezenweb.domain.dto.GuestbookDto;
import com.Ezenweb.service.BoardService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController // @Controller + @ResourceBody
@RequestMapping("/board")
public class BoardController {
    
    // --------------------------- 1. 전역변수 ----------------------------------------------

    // 1. 서비스 메소드 사용을 위한 객체 생성
    @Autowired // 어노테이션을 이용해서 spring 컨테이너에 빈[메모리] 생성
    private BoardService boardService;


    // --------------------------- 2. 페이지 요청 [view] ------------------------------------
    
    // 1. 게시판 전체 페이지 열기
    @GetMapping("/list") // URL : localhost:8080/board/list 주소를 입력 시 해당 html 반환
    public Resource getlist(){
        return new ClassPathResource("templates/board/list.html");
    }
    // 2. 게시물 작성 페이지 열기
    @GetMapping("/write")
    public Resource getwrite(){
        return new ClassPathResource("templates/board/write.html");
    }
    // 3. 게시물 개별 조회 페이지 열기
    @GetMapping("/view")
    public Resource getview(){
        return new ClassPathResource("templates/board/view.html");
    }
    // 4. 게시물 수정 페이지 열기
    @GetMapping("/update")
    public Resource getupdate(){
        return new ClassPathResource("templates/board/update.html");
    }

    // 5. 방명록  페이지 열기
    @GetMapping("/guestbook")
    public Resource getguest(){
        return new ClassPathResource("templates/board/guestbook.html");
    }


    // --------------------------- 3. 요청과 응답 [model] -----------------------------------
    
    // *** HTTP 데이터 요청 메소드 매핑 : @RequestBody @RequestParam @PathVariable[경로상요청시]
    
    // 1. 게시물 작성 [ 첨부파일 ]
    @PostMapping("/setboard")
    public boolean setboard( @RequestBody BoardDto boardDto ){
        System.out.println( "컨트롤러 디티오 화긴 :: "+ boardDto );
        return boardService.setboard( boardDto );
    }
    // 2. 게시물 목록 조회 [ 페이징, 검색 ]
    @GetMapping("/boardlist")
    public List< BoardDto > boardlist( @RequestParam("bcno") int bcno ){
        return boardService.boardlist( bcno );
    }
    // 3. 게시물 개별 조회
    @GetMapping("/getboard")
    public BoardDto getboard( @RequestParam("bno") int bno ){
        return boardService.getboard( bno );
    }
    // 4. 게시물 삭제
    @DeleteMapping("/delboard")
    public boolean delboard( @RequestParam("bno") int bno ){
        return boardService.delboard( bno );
    }
    // 5. 게시물 수정 [ 첨부파일 ]
    @PutMapping("/upboard")
    public boolean upboard( @RequestBody BoardDto boardDto ){
        System.out.println(" 컨트롤 수정::"+boardDto);
        return boardService.upboard( boardDto );
    }

    // 6. 카테고리 등록
    @PostMapping("/setbcategory")
    public boolean setbcategory( @RequestBody BcategoryDto bcategoryDto ){
        System.out.println("컨트롤러 ::: " + bcategoryDto);
        return boardService.setbcategory( bcategoryDto );
    }

    // 7. 모든 카테고리 출력
    @GetMapping("/bcategorylist")
    public List<BcategoryDto> bcategorylist(){
        return boardService.bcategorylist();
    }


    // 8. 방명록 작성
    @PostMapping("/setguestbook")
    public boolean setguestbook(@RequestBody GuestbookDto guestbookDto ){
        return boardService.setguestbook( guestbookDto );

    }

    // 9. 방명록 출력
    @GetMapping("/getguestbook")
    public List< GuestbookDto > getguestbook(){
        return boardService.getguestbook();
    }

    // 10. 방명록 카테고리 등록
    @PostMapping("/setgustcategory")
    public boolean setgustcategory(@RequestBody GuestbookCgDto guestbookCgDto ){
        return boardService.setgustcategory( guestbookCgDto );
    }

    // 11. 방명록 카테고리 출력
    @GetMapping("getgustcategorylist")
    public List< GuestbookCgDto > getgustcategorylist(){
        return boardService.getgustcategorylist();
    }



}
