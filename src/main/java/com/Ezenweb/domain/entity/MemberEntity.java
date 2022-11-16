package com.Ezenweb.domain.entity;

import com.Ezenweb.domain.dto.MemberDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity // 해당 클래스와 연결된 DB의 테이블과 매핑[연결]
@Table(name="member") // DB에서 사용될 테이블 이름
public class MemberEntity {

    // 1. 필드
    @Id // 엔티티 당 무조건 1개 이상 [PK]
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동번호 부여
    private int mno;            // 회원번호 필드

    @Column(nullable = false)   // not null
    private String memail;      // 회원 이메일 = 아이디 필드

    @Column(nullable = false)   // not null
    private String mpw;         // 회원 비밀번호 필드

    @Column(nullable = false)   // not null
    private String mphone;      // 회원 전화번호 필드


    // 2. 생성자

    // 3. 메소드

    // * 엔티티 --> Dto
    public MemberDto toDto(){
        return MemberDto.builder() // 빌더 메소드 시작
                .mno( this.mno )
                .memail( this.memail )
                .mpw( this.mpw )
                .mphone( this.mphone )
                .build(); // 빌드 메소드 끝
    }

}
