package com.Ezenweb.service;

import com.Ezenweb.domain.dto.GuestbookCgDto;
import com.Ezenweb.domain.dto.GuestbookDto;
import com.Ezenweb.domain.entity.bcategory.GuestbookCgEntity;
import com.Ezenweb.domain.entity.bcategory.GuestbookCgRepository;
import com.Ezenweb.domain.entity.board.GuestbookEntity;
import com.Ezenweb.domain.entity.board.GuestbookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GuestbookService {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private GuestbookRepository guestbookRepository; // 비회원게시판 리포지토리

    @Autowired
    private GuestbookCgRepository guestbookCgRepository; // 비회원게시판 카테고리 리포지토리


    // [ 첨부파일 경로 ]
    String path = "C:\\Users\\504\\IdeaProjects\\SpringWeb\\src\\main\\resources\\static\\gbuploadimg\\";

    // -------------------------------- 비회원제 게시판 ---------------------------------
    // 1. 방명록 작성
    @Transactional
    public boolean setguestbook( GuestbookDto guestbookDto ){
        System.out.println("서비스 guestbookDto :: "+ guestbookDto );
        // 카테고리 번호 불러오기
        Optional<GuestbookCgEntity> optional = guestbookCgRepository.findById( guestbookDto.getGbcno() );

        if( !optional.isPresent() ){ // 카테고리 번호가 없으면
            return false;
        }
        GuestbookCgEntity gbCgEntity = optional.get(); // FK에 넣어줄 값 가지고있기

        // 입력된 내용 엔티티에 저장하기
        GuestbookEntity guestbookEntity = guestbookRepository.save( guestbookDto.toGuestbookEntity());
        System.out.println("서비스 guestbookEntity :: "+guestbookEntity);
        
        if ( guestbookEntity.getGbno() != 0 ){ // 게시물 번호가 0이 아니면 성공
            System.out.println("guestbookEntity.getGbno() :: "+ guestbookEntity.getGbno() );
            
            if ( guestbookDto.getGbfile() != null ){ // 파일이 있으면~
                
                System.out.println("guestbookDto.getGbfile() :: "+ guestbookDto.getGbfile() );
                // 업로드 파일 이름!
                String uuid = UUID.randomUUID().toString(); // 난수생성
                String filename = uuid+"_"+guestbookDto.getGbfile().getOriginalFilename();

                // db 저장
                guestbookEntity.setGbfile( filename );

                File upfile = new File( path+filename );
                try {
                    guestbookDto.getGbfile().transferTo( upfile );
                } catch (IOException e) {
                    System.out.println( "업로드 오류 "+e );
                }
            }

            guestbookEntity.setGuestbookCgEntity( gbCgEntity ); // FK에 데이터 넣어주기
            gbCgEntity.getGuestbookEntityList().add( guestbookEntity );
            return true;
        }else {
            return false; // 0이면 실패
        }
    }

    // 2. 방명록 출력
    @Transactional
    public List< GuestbookDto > getguestbook(int gbcno ){
        List< GuestbookEntity > gbEntities = null;

        if( gbcno == 0 ){
            gbEntities = guestbookRepository.findAll(); // 0이면 전체보기
        }else { // 카테고리별보기
            GuestbookCgEntity gbCgEntity = guestbookCgRepository.findById( gbcno ).get(); // 선택된 카테고리 번호 가져와서 저장
            gbEntities = gbCgEntity.getGuestbookEntityList(); // 해당 엔티티의 게시물 목록 호출
        }
        List< GuestbookDto > guestbookDtos = new ArrayList<>();
        for( GuestbookEntity entity : gbEntities){
            guestbookDtos.add( entity.toGuestbookDto() );
        }
        return guestbookDtos;
    }

    // 3. 방명록 카테고리 등록
    @Transactional
    public boolean setgustcategory( GuestbookCgDto guestbookCgDto ){
        GuestbookCgEntity gbCgEntity = guestbookCgRepository.save( guestbookCgDto.togbcEntity());
        return true;
    }

    // 4. 방명록 카테고리 출력
    @Transactional
    public List< GuestbookCgDto > getgustcategorylist(){
        List < GuestbookCgEntity > entityList = guestbookCgRepository.findAll();
        List < GuestbookCgDto > guestbookCgDtos = new ArrayList<>();
        for( GuestbookCgEntity entity : entityList ){
            guestbookCgDtos.add( entity.togbcDto() );
        }
        return guestbookCgDtos;
    }

    // 5. 방명록 게시글 수정
    @Transactional
    public boolean gbupdate( GuestbookDto guestbookDto ){
        Optional < GuestbookEntity > optional = guestbookRepository.findById( guestbookDto.getGbno() ); // 게시물번호 가져오기

        if( optional.isPresent() ){ // 게시글 번호가 존재할 경우
            GuestbookEntity gbentity = optional.get();
            gbentity.setGbcontent( guestbookDto.getGbcontent() );
            gbentity.setGbname( guestbookDto.getGbname() );
            return true;
        }else {
            return false;
        }
    }

    // 6. 방명록 게시글 삭제
    @Transactional
    public boolean gbdelete (int gbno ){
        Optional < GuestbookEntity > optional = guestbookRepository.findById( gbno );
        if ( optional.isPresent() ){
            GuestbookEntity entity = optional.get(); // 선택된 게시글
            guestbookRepository.delete( entity ); // 리포지토리에서 삭제해줘
            return true;
        }else {
            return false;
        }
    }





















}
