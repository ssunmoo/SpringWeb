package com.Ezenweb.service;

import com.Ezenweb.domain.dto.RoomDto;
import com.Ezenweb.domain.entity.member.MemberEntity;
import com.Ezenweb.domain.entity.member.MemberRepository;
import com.Ezenweb.domain.entity.room.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoomService {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Transactional
    public boolean write(RoomDto roomDto){

        // 1. 로그인한 유저
        String loginMemail = memberService.getloginMno();
        if ( memberService.getloginMno() == null ){
            return false;
        }
        MemberEntity memberEntity = memberRepository.findByMemail( loginMemail ).get();

        // 2. 룸 등록[pk]

        // 3. 사진 등록[fk]

        return true;
    }
}
