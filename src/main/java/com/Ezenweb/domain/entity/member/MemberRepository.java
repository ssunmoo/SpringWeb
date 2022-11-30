package com.Ezenweb.domain.entity.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // 해당 인터페이스가 리포지토리임을 명시
public interface MemberRepository extends JpaRepository< MemberEntity, Integer > {
                                // extends JpaRepository< 엔티티클래스명, PK필드자료형 > ,

    // 1. 이메일을 이용한 엔티티 검색 메소드 구현
    Optional<MemberEntity> findByMemail(String memail);



}

