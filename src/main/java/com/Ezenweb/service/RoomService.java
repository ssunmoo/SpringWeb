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

import java.util.ArrayList;
import java.util.List;

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
                    RoomImgEntity.builder().rimg( img.getOriginalFilename()).build()
                ); // 필드가 적을때는 굳이 dto 필요 없음
                roomEntity.getRoomImgEntityList().add(roomImgEntity);
                roomImgEntity.setRoomEntity( roomEntity );
            }
        });
        return true;
    }

    // 2. 룸 출력
    public List<RoomDto> getroomlist(){
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
