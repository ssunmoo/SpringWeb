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
import org.hibernate.metamodel.model.domain.internal.MapMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;


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
    private HttpServletResponse response; // 4. 리스폰 선언
    
    @Autowired
    private BcategoryRepository bcategoryRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private GuestbookRepository guestbookRepository; // 비회원게시판 리포지토리

    @Autowired
    private GuestbookCgRepository guestbookCgRepository; // 비회원게시판 카테고리 리포지토리

    // [ 첨부파일 경로 ]
    String path = "C:\\Users\\504\\IdeaProjects\\SpringWeb\\src\\main\\resources\\static\\bupload\\";

    // --------------------------- 2. 서비스 ----------------------------------------------

    // 0. 첨부파일 다운로드
    public void filedownload( String filename ){

        // ** UUID 제거
        String realfilename = "";
        String [] split = filename.split("_"); // 1. 앞 언더바(_) 기준으로 자르기
        for( int i = 1; i < split.length; i++ ){     // 2. uuid 제와한 반복문 돌리기 1번째 인덱스부터
            realfilename += split[i];                // 3. 뒷자리 문자열 추가
            if ( split.length-1 != i ){              // 4. 마지막 인덱스가 아니면
                realfilename += "_";                 // 5. 문자열[1]_문자열[2]_문자열[3]
            }
        }

        // 1. 경로 찾기
        String filepath = path + filename;
        // 2. 헤더 구성 [ HTTP 에서 지원하는 다운로드 형식 메소드 response ]
        try {
            response.setHeader(
                    "Content-Disposition",  // 다운로드 형식의 이름 [ 브라우저마다 다르며 정해져있음 ]
                    "attachment;filename=" + URLEncoder.encode( realfilename, "UTF-8")); // 다운로드에 표시된 파일명

            File file = new File( filepath ); // 해당 경로의 파일 객체화
            // 3. 다운로드 스트림
            // 3-1. 입력 스트림 객체 선언
            BufferedInputStream inputStream = new BufferedInputStream( new FileInputStream( file ) );
            // 3-2. 파일의 길이만큼 배열 선언
            byte[] bytes = new byte[ (int) file.length()];
            // 3-3. 파일의 길이만큼 읽어와서 바이트 배열에 저장
            inputStream.read( bytes ); // * 스트림 읽기
            // 4. 출력 스트림
            BufferedOutputStream outputStream = new BufferedOutputStream( response.getOutputStream() );
            // 5. 응답하기 [ 내보내기 ]
            outputStream.write( bytes ); // * 스트림 내보내기
            // 6. 버퍼 초기화 & 스트림 닫기
            outputStream.flush(); // 초기화
            outputStream.close();
            inputStream.close();

        } catch (Exception e) {
            System.out.println("첨부파일 다운로드 실패 : " + e);
        }
    }

    // * 첨부파일업로드 [ 1. 쓰기, 수정 메소드 사용 ]
    @Transactional
    public boolean fileupload( BoardDto boardDto , BoardEntity boardEntity ) {
        if ( boardDto.getBfile() != null ) {  // ** 첨부파일 있을때
            // ** 업로드된 파일의 이름 [ ** 문제점 : 파일 명 중복 ]
            String uuid = UUID.randomUUID().toString(); // 난수 생성
            String filename = uuid + "_" + boardDto.getBfile().getOriginalFilename(); // 2. 난수+파일명

            // ** 첨부 파일명 DB 등록
            boardEntity.setBfile(filename); // 3. 난수+파일명 엔티티에 저장

            // ** 업로드
            try {
                // 4. 경로 + 파일명 [ 객체화 ]
                File uploadfile = new File(path + filename);
                boardDto.getBfile().transferTo(uploadfile); // 5. 해당 경로로 업로드
            } catch (IOException e) {
                System.out.println("첨부파일 업로드 실패");
            }
            return true;
        } else {
            return false;
        }
    }

    // 1. 게시물 작성
    @Transactional
    public boolean setboard( BoardDto boardDto ){
        // 1. 회원 정보 가져오기
        MemberEntity memberEntity = memberService.getEntity();
        if( memberEntity == null ) {
            return false;
        }
        // 2. 선택한 카테고리 정보 가져오기 -> 카테고리 엔티티 검색
        Optional< BcategoryEntity> optional = bcategoryRepository.findById( boardDto.getBcno() );
        if( !optional.isPresent() ){ // 내용물[엔티티]가 없면
            return false;
        }
        BcategoryEntity bcategoryEntity = optional.get(); // dto -> entity [ INSERT ]
        // 3. dto -> entity [ INSERT ] 반환 저장된 entity 반환
        BoardEntity boardEntity = boardRepository.save( boardDto.toEntity() ); // 클래스명.메소드명(); -> 메소드가 static 일때만 가능

        if( boardEntity.getBno() != 0 ){ // 4. 생성된 entity의 게시물 번호가 0이 아니면 성공
            fileupload( boardDto, boardEntity ); // 업로드 함수 실행

            // 회원 <--> 게시물 연관관계 대입
            boardEntity.setMemberEntity( memberEntity ); // FK 대입
            // 양방향 [ PK 필드에 FK 연결 ]
            memberEntity.getBoardEntityList().add( boardEntity ); // 내가 쓴 글을 확인할 수 있음

            // 2. 카테고리 <-> 게시물 연관관계 대입
            boardEntity.setBcategoryEntity( bcategoryEntity );
            bcategoryEntity.getBoardEntityList().add( boardEntity );
            return true;
        }else{
            return false;
        }
    }


    // 2. 게시물 목록 조회 [ 페이징, 검색 ]
    @Transactional              // bcno, 카테고리번호 / page 현재 페이지 번호
    public List< BoardDto > boardlist( int bcno, int page, String key, String keyword){
        
        Page< BoardEntity > elist = null; // 1. 페이징 처리된 entity 객체 생성
        Pageable pageable = PageRequest.of( page-1, 3, Sort.by( Sort.Direction.DESC, "bno")); // 2. 페이징 설정 시작 : 0부터
        
        // 3. 검색 여부 판단
        if( key.equals("btitle")){          // 검색 필드가 제목이면
            // 1. 검색 시 : [조건1 : 제목 검색 , 조건2 : 카테고리 검색]
            elist = boardRepository.findBybtitle( bcno, keyword, pageable );
        } else if (key.equals("bcontent")) { // 검색 필드가 내용이면
            // 2. 검색 시 : [조건1 : 제목 검색 , 조건2 : 카테고리 검색]
            elist = boardRepository.findBybcontent( bcno, keyword, pageable );
        } else { // 3. 검색이 없으면
            if( bcno == 0 ){
                elist = boardRepository.findAll( pageable ); // 전체 검색
            } else {
                elist = boardRepository.findBybcno( bcno, pageable ); // 카테고리 검색
            }
        }
        // 프론트엔드에 표시할 페이징 번호 버튼 수
        int btncount = 5;                               // 1. 페이지에 표시할 총 페이지 버튼 개수 [ 5개씩 표시 ]
        int startbtn = ( page/btncount ) * btncount +1; // 2. 시작번호 버튼
        int endbtn = startbtn + btncount-1;             // 3. 마지막번호 버튼
        if( endbtn > elist.getTotalPages() ) endbtn = elist.getTotalPages();

        // ** 검색 페이징된 결과 확인 **
        System.out.println("엔티티들 : " + elist );
        System.out.println("총엔티티수 : " + elist.getTotalElements() );
        System.out.println("총페이지수 : " + elist.getTotalPages() );
        System.out.println("현재페이지수 : " + elist.getNumber() );
        System.out.println("현재 엔티티 객체정보 : " + elist.getContent() );
        System.out.println("현재 페이지의 게시물수 : " + elist.getNumberOfElements() );
        System.out.println("현재 페이지가 첫페이지인지 여부확인 : " + elist.isFirst() );
        System.out.println("현재 페이지가 마지막인지 여부확인 : " + elist.isLast() );


        // 2. 컨트롤에게 전달할때 형변환하기 entity -> dto로 변경
        List< BoardDto > dlist = new ArrayList<>(); // 메모리 할당
        // 3. 변환
        for( BoardEntity entity : elist ){
            dlist.add( entity.toDto() );
        }

        dlist.get(0).setStartbtn( startbtn );
        dlist.get(0).setEndbtn( endbtn );

        // 4. dto로 변환된 dlist 반환
        return dlist;

        //        if( bcno == 0 ){ // 1. 카테고리 번호가 0 이면 전체보기
//            elist = boardRepository.findAll( pageable ); // 모든 엔티티 호출
//        }
//          // 2. 카테고리 번호가 0이 아니면 선택된 카테고리별 보기
//        else{
//            // 1. 페이징 처리 전
////          BcategoryEntity bcEntity = bcategoryRepository.findById( bcno ).get();
////          elist = bcEntity.getBoardEntityList(); // 해당 엔티티의 게시물 목록 호출
//
//            // 2. 페이징 처리 후
//            elist = boardRepository.findBybcno( bcno, pageable );
//        }
//        System.out.println("전체 페이지 수 " + elist.getTotalPages());
//        System.out.println("전체 게시물 수 " + elist.getTotalElements());
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
    public boolean delboard( int bno ) {
        Optional<BoardEntity> optional = boardRepository.findById(bno);
        if (optional.isPresent()) {
            BoardEntity boardEntity = optional.get();

            // 첨부파일 같이 삭제
            if (boardEntity.getBfile() != null) {    // 기존 첨부파일 있을 떄
                File file = new File(path + boardEntity.getBfile()); // 기존 첨부파일 객체화
                if ( file.exists() ) { // 존재하면
                    file.delete();   // 파일 삭제
                }
            }
            boardRepository.delete(boardEntity); // 찾은 엔티티를 삭제
            return true;
        }else {
            return false;
        }
    }

    // 5. 게시물 수정 [ 첨부파일 1. 첨부파일이 있을때 수정, 2. 첨부파일이 없을때 추가 ]
    @Transactional
    public boolean upboard( BoardDto boardDto ){
        // 1. Dto에서 수정할 pk 번호 이용해서 엔티티 찾기
        Optional< BoardEntity > optional = boardRepository.findById( boardDto.getBno() );
        if( optional.isPresent() ){
            BoardEntity boardEntity = optional.get();

            // 1. 수정할 첨부파일이 있을 때 : 기존 파일 삭제 후 새로 업로드
            if( boardDto.getBfile() != null){       // 수정할 정보
                if( boardEntity.getBfile() != null ){    // 기존 첨부파일 있을 떄
                    File file = new File( path + boardEntity.getBfile() ); // 기존 첨부파일 객체화
                    if( file.exists()) { // 존재하면
                        file.delete();   // 파일 삭제
                    }
                    // 기존 첨부파일이 없을 때
                    fileupload( boardDto, boardEntity ); // 업로드 함수 실행
                }
            }

            // * 수정 처리 [ 메소드별도 존재x ] 엔티티 객체 자체를 수정 [ entity <-매핑-> dto ]
            boardEntity.setBtitle( boardDto.getBtitle() );
            boardEntity.setBcontent( boardDto.getBcontent() );
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








}



