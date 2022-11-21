package com.Ezenweb.service;

import com.Ezenweb.domain.dto.BcategoryDto;
import com.Ezenweb.domain.dto.BoardDto;
import com.Ezenweb.domain.dto.GuestbookCgDto;
import com.Ezenweb.domain.dto.GuestbookDto;
import com.Ezenweb.domain.entity.bcategory.BcategoryEntity;
import com.Ezenweb.domain.entity.bcategory.BcategoryRepository;
import com.Ezenweb.domain.entity.bcategory.GuestbookCgEntity;
import com.Ezenweb.domain.entity.bcategory.GuestbookCgRepository;
import com.Ezenweb.domain.entity.board.BoardEntity;
import com.Ezenweb.domain.entity.board.BoardRepository;
import com.Ezenweb.domain.entity.board.GuestbookEntity;
import com.Ezenweb.domain.entity.board.GuestbookRepository;
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

    @Autowired
    private BcategoryRepository bcategoryRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private GuestbookRepository guestbookRepository; // 비회원게시판 리포지토리

    @Autowired
    private GuestbookCgRepository guestbookCgRepository; // 비회원게시판 카테고리 리포지토리


    // --------------------------- 2. 서비스 ----------------------------------------------

    // 1. 게시물 작성 [ 첨부파일 ]
    @Transactional
    public boolean setboard( BoardDto boardDto ){

        // 1. 회원 정보 가져오기
        MemberEntity memberEntity = memberService.getEntity();
        if( memberEntity == null ) {
            return false;
        }
        // 2. 선택한 카테고리 정보 가져오기 -> 카테고리 엔티티 검색
        Optional< BcategoryEntity> optional = bcategoryRepository.findById( boardDto.getBcno() );
        System.out.println("선택한 카테고리번호 1 "+ optional );
        if( !optional.isPresent() ){ // 내용물[엔티티]가 없면
            return false;
        }
        BcategoryEntity bcategoryEntity = optional.get(); // dto -> entity [ INSERT ]
        System.out.println("선택한 카테고리번호 2 "+ bcategoryEntity );
        // 3. dto -> entity [ INSERT ] 반환 저장된 entity 반환
        BoardEntity boardEntity = boardRepository.save( boardDto.toEntity() ); // 클래스명.메소드명(); -> 메소드가 static 일때만 가능

        if( boardEntity.getBno() != 0 ){ // 4. 생성된 entity의 게시물 번호가 0이 아니면 성공

            // 1. 회원 <-> 게시물 연관관계 대입
            boardEntity.setMemberEntity( memberEntity ); // FK 대입
            // 양방향 [ PK 필드에 FK 연결 ]
            memberEntity.getBoardEntityList().add( boardEntity ); // 내가 쓴 글을 확인할 수 있음

            // 2. 카테고리 <-> 게시물 연관관계 대입
            boardEntity.setBcategoryEntity( bcategoryEntity );
            bcategoryEntity.getBoardEntityList().add( boardEntity );

            return true;
        }else{
            return false; // 0이면 실패
        }
    }


    // 2. 게시물 목록 조회 [ 페이징, 검색 ]
    @Transactional
    public List< BoardDto > boardlist( int bcno ){
        List< BoardEntity > elist = null;
        
        if( bcno == 0 ){ // 1. 카테고리 번호가 0 이면 전체보기
            elist = boardRepository.findAll(); // 모든 엔티티 호출
        }else{ // 카테고리 번호가 0이 아니면 선택된 카테고리별 보기
            BcategoryEntity bcEntity = bcategoryRepository.findById( bcno ).get();
            elist = bcEntity.getBoardEntityList(); // 해당 엔티티의 게시물 목록 호출
        }

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
    

    // 6. 카테고리 등록
    @Transactional
    public boolean setbcategory( BcategoryDto bcategoryDto ){
        BcategoryEntity entity = bcategoryRepository.save( bcategoryDto.toEntity() );
        System.out.println("서비스 ::: " + entity );

        if( entity.getBcno() != 0 ){
            return true;
        }else {
            return false;
        }
    }

    // 7. 모든 카테고리 출력
    @Transactional
    public List<BcategoryDto> bcategorylist(){
        List< BcategoryEntity > entityList = bcategoryRepository.findAll(); // 전체를 꺼내와서 entity 리스트에 담기

        List< BcategoryDto > dtoList = new ArrayList<>();

        entityList.forEach( e -> dtoList.add( e.toDto() ) );
        return dtoList;

//        // ** 리스트를 순회하는 for문 3가지
//        for( int i = 0; i < entityList.size(); i++ ){
//            BcategoryEntity e = entityList.get(i);
//            System.out.println( e.toString() );
//        }
//
//        for ( BcategoryEntity e : entityList ){
//            System.out.println( e.toString() );
//        }
//
//        entityList.forEach( e ->
//            e.toString()
//        );

        // entityList.forEach( Consumer < ? super BcategoryEntity > action );
        // [ 화살표 함수 / 람다식 ] js : (매개변수) => { 실행코드 } , java : 매개변수 -> { 실행코드 }

    }


    // 8. 방명록 작성
    @Transactional
    public boolean setguestbook( GuestbookDto guestbookDto ){

        // 카테고리 번호 불러오기
        Optional< GuestbookCgEntity > optional = guestbookCgRepository.findById( guestbookDto.getGbcno() );
        if( optional.isPresent() ){
            return false;
        }
        GuestbookCgEntity gbCgEntity = optional.get();
        // 입력된 내용 엔티티에 저장하기
        GuestbookEntity guestbookEntity = guestbookRepository.save( guestbookDto.toGuestbookEntity());
        if ( guestbookEntity.getGbno() != 0 ){ // 게시물 번호가 0이 아니면 성공
            guestbookEntity.setGuestbookCgEntity( gbCgEntity );
            gbCgEntity.getGuestbookEntityList().add( guestbookEntity );
            return true;
        }
        return false; // 0이면 실패
    }

    // 9. 방명록 출력
    @Transactional
    public List< GuestbookDto > getguestbook(){
        List< GuestbookEntity > gbEntities = guestbookRepository.findAll();
        List< GuestbookDto > guestbookDtos = new ArrayList<>();

        for( GuestbookEntity entity : gbEntities){
            guestbookDtos.add( entity.toGuestbookDto() );
        }
        return guestbookDtos;
    }

    // 10. 방명록 카테고리 등록
    @Transactional
    public boolean setgustcategory( GuestbookCgDto guestbookCgDto ){
        GuestbookCgEntity gbCgEntity = guestbookCgRepository.save( guestbookCgDto.togbcEntity());
        return true;
    }


    // 11. 방명록 카테고리 출력
    @Transactional
    public List< GuestbookCgDto > getgustcategorylist(){
        List < GuestbookCgEntity > entityList = guestbookCgRepository.findAll();
        List < GuestbookCgDto > guestbookCgDtos = new ArrayList<>();
        for( GuestbookCgEntity entity : entityList ){
            guestbookCgDtos.add( entity.togbcDto() );
        }
        return guestbookCgDtos;

    }



}



