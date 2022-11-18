package com.Ezenweb.service;

import com.Ezenweb.domain.dto.BoardDto;
import com.Ezenweb.domain.entity.board.BoardEntity;
import com.Ezenweb.domain.entity.board.BoardRepository;
import com.Ezenweb.domain.entity.member.MemberEntity;
import com.Ezenweb.domain.entity.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service // 컴포넌트 [ 스프링이 mvc 관리 할 수 있도록 ]
public class BoardService {

    // --------------------------- 1. 전역변수 ----------------------------------------------

    @Autowired // 1. 게시판 리포지토리 메소드 사용을 위한 객체 생성
    private BoardRepository boardRepository;
            // @Transactional : 엔티티 조작[셀/인/업/델] 시 사용되는 트랜잭션
    @Autowired
    private MemberRepository memberRepository;  // 2. 회원 리포지토리 객체 선언

    @Autowired
    private HttpServletRequest request; // 3. 세션 요청 객체 선언



    // --------------------------- 2. 서비스 ----------------------------------------------

    // 1. 게시물 작성 [ 첨부파일 ]
    @Transactional
    public boolean setboard( BoardDto boardDto ){
        // 1. 로그인 정보 확인
        Object object = request.getSession().getAttribute("loginMno");
        if( object == null ){
            return false;
        }else {
            // 2. 로그인된 회원번호 호출
            int mno = (Integer)object;
            
            // 3. 회원번호 --> 회원정보 호출
            Optional<MemberEntity > optional = memberRepository.findById( mno );
            if( !optional.isPresent() ){
                return  false;
            }
            // 4. 로그인된 회원의 엔티티
            MemberEntity memberEntity = optional.get();

            // 5. dto -> entity [ INSERT ] 반환 저장된 entity 반환
            BoardEntity boardEntity = boardRepository.save( boardDto.toEntity() );
            // 클래스명.메소드명(); -> 메소드가 static 일때만 가능

            // 6 생성된 entity의 게시물 번호가 0이 아니면 성공
            if( boardEntity.getBno() != 0 ){
                // FK 대입
                boardEntity.setMemberEntity( memberEntity );
                // 양방향 [ PK 필드에 FK 연결 ]
                memberEntity.getBoardEntityList().add( boardEntity ); // 내가 쓴 글을 확인할 수 있음
                return true;
            }else{
                return false; // 0이면 실패
        }

        }
    }

    // 2. 게시물 목록 조회 [ 페이징, 검색 ]
    @Transactional
    public List< BoardDto > boardlist(){
        // 1. 모든 엔티티 호출
        List< BoardEntity > elist = boardRepository.findAll();

        // 2. 컨트롤에게 전달할때 형변환하기 entity -> dto로 변경
        List< BoardDto > dlist = new ArrayList<>(); // 메모리 할당

        // 3. 변환
        for( BoardEntity entity : elist ){
            dlist.add( entity.toDto() );
        }
        // 4. dto로 변환된 dlist 반환
        return dlist;
    }

    // 3. 게시물 개별 조회
    @Transactional
    public BoardDto getboard( int bno ){ // 게시물 번호를 매개변수로 받기
        // 1. 입력받은 게시물 번호로 엔티티 검색 [ Optional ]
        Optional < BoardEntity > optional = boardRepository.findById( bno );
        // 2. Optional 안에 있는 내용물 확인
        if( optional.isPresent() ){
            // 3. 엔티티 꺼내기
            BoardEntity boardEntity = optional.get();
            // 4. 형변환하여 반환
            return boardEntity.toDto();
        }else {
            return null;
        }
    }

    // 4. 게시물 삭제
    @Transactional
    public boolean delboard( int bno ){
       Optional< BoardEntity > optional = boardRepository.findById( bno );
       if( optional.isPresent() ){
            BoardEntity entity = optional.get();
            boardRepository.delete( entity ); // 찾은 엔티티를 삭제
            return true;
       } return false;
    }

    // 5. 게시물 수정 [ 첨부파일 ]
    @Transactional
    public boolean upboard( BoardDto boardDto ){
        // 1. Dto에서 수정할 pk 번호 이용해서 엔티티 찾기
        Optional< BoardEntity > optional = boardRepository.findById( boardDto.getBno() );

        if( optional.isPresent() ){
            BoardEntity entity = optional.get();
            // * 수정 처리 [ 메소드별도 존재x ] 엔티티 객체 자체를 수정 [ entity <-매핑-> dto ]
            entity.setBtitle( boardDto.getBtitle() );
            entity.setBcontent( boardDto.getBcontent() );
            entity.setBfile( boardDto.getBfile() );
            return true;
        }else {
            return false;
        }
    }
    










}



