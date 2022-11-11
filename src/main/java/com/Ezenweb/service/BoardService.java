package com.Ezenweb.service;

import com.Ezenweb.domain.dao.BoardDao;
import com.Ezenweb.domain.dto.BoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;

@Service    // 컴포넌트 [ 스프링이 mvc 관리 할 수 있도록 ]
public class BoardService {

//    @Autowired  // 메모리 자동 할당
//    BoardDao boardDao;

    // 1. 게시물 등록 서비스
    public boolean setboard( @RequestBody BoardDto boardDto ) {
       return new BoardDao().setboard( boardDto );
    } // setboard e
    
   //  2. 게시물 리스트 서비스
    public ArrayList< BoardDto > getboards() {
        return new BoardDao().getboards();
    }
    
    
    
    
}



