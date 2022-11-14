package com.Ezenweb.service;

import com.Ezenweb.domain.dto.MemberDto;
import com.Ezenweb.domain.entity.MemberEntity;
import com.Ezenweb.domain.entity.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service    // 해당 클래스가 서비스임을 명시
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public int setmember( MemberDto memberDto ) {

        // 1. Dao 처리 // Dto --> entity로 변환해야 함
        MemberEntity entity = memberRepository.save( memberDto.toEntity() );
        // memberRepository.save( 엔티티객체 ) : 해당 엔티티 객체가 해당 테이블에 insert 되며 생성된 엔티티 반환
        entity.setMemail("asdf@aaa.aaa");
        return entity.getMno();
    }


}
