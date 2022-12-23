package com.Ezenweb.service;

import com.Ezenweb.domain.dto.RoomDto;
import com.Ezenweb.domain.entity.member.MemberEntity;
import com.Ezenweb.domain.entity.member.MemberRepository;
import com.Ezenweb.domain.entity.room.RoomEntity;
import com.Ezenweb.domain.entity.room.RoomImgEntity;
import com.Ezenweb.domain.entity.room.RoomImgRepository;
import com.Ezenweb.domain.entity.room.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RoomService {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomImgRepository roomImgRepository;


    // 현재 스프링의 배포된 내장서버 폴더
    // String path ="C:\\프로젝트경로\\build\\resources\\main\\static\\bupload\\";
    // String path ="C:\\Users\\504\\IdeaProjects\\SpringWeb\\build\\resources\\main\\static\\bupload\\";

    // 현재 스프링에 배포된 내장 서버 내 리액트 리소스 [ view ] 소스
    String path ="C:\\Users\\504\\IdeaProjects\\SpringWeb\\build\\resources\\main\\static\\static\\media\\";

    @Transactional
    public boolean write(RoomDto roomDto){

        // 1. 로그인한 유저
        String loginMemail = memberService.getloginMno();
        if ( memberService.getloginMno() == null ){
            return false;
        }
        MemberEntity memberEntity = memberRepository.findByMemail( loginMemail ).get();

        // 2. 룸 등록[pk]
        RoomEntity roomEntity = roomRepository.save( roomDto.toEntity() );
        if( roomEntity.getRno() < 1 ){
            return false;
        }
        roomEntity.setMemberEntity( memberEntity ); // 룸엔티티에 멤버엔티티 넣기
        memberEntity.getRoomEntityList().add(roomEntity); // 멤버 엔티티 룸리스트에 룸엔티티 넣기

        // 3. 사진 등록[fk]
        roomDto.getRimg().forEach( (img) ->{ // 첨부파일이 여러개일 경우 혹은 존재할 경우
            if( !img.getOriginalFilename().equals("") ){
                RoomImgEntity roomImgEntity = roomImgRepository.save(
                    RoomImgEntity.builder().build()
                ); // 필드가 적을때는 굳이 dto 필요 없음
                // 룸에 사진 엔티티 대입
                roomEntity.getRoomImgEntityList().add(roomImgEntity);
                roomImgEntity.setRoomEntity( roomEntity );

                try {
                // 첨부파일 사진 업로드
                    String filename = roomImgEntity.getRimgno()+img.getOriginalFilename(); // 첨부파일된 실체 파일명
                    roomImgEntity.setRimg(filename); // db에 저장
                    File file = new File(path+filename); // 경로 + 첨부파일명 => file 객체화[ transferTo 함수의 인수 file]
                    img.transferTo(file); // transferTo : 파일 이동 함수
                    // 기존파일.transferTo( 이동할경로 )
                } catch (Exception e) {
                    System.out.println("사진 업로드 실패 " + e );
                }
            }
        });
        return true;
    }

    // 2. 룸 출력
    public List<RoomDto> getroomlist(){
//
//        Map<String , Integer> list = roomRepository.findBySearch();
//        for( String s :  list.keySet() ) {
//            System.out.println( s );
//            System.out.println( list.get(s));
//        }

        // 1. 모든 룸 꺼내기
        List<RoomEntity> roomEntityList = roomRepository.findAll();
        // 출력용 디티오
        List<RoomDto> roomDtoList = new ArrayList<>();
        // 2. 형변환
        roomEntityList.forEach( (e) -> {
            roomDtoList.add( e.toRoomDto() );
        });
        return roomDtoList;
    }







}
