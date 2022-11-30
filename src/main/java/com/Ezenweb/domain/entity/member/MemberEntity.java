package com.Ezenweb.domain.entity.member;

import com.Ezenweb.domain.dto.MemberDto;
import com.Ezenweb.domain.entity.BaseEntity;
import com.Ezenweb.domain.entity.board.BoardEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity // 해당 클래스와 연결된 DB의 테이블과 매핑[연결]
@Table(name="member") // DB에서 사용될 테이블 이름
public class MemberEntity extends BaseEntity { // 베이스엔티티 상속받기

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

    @OneToMany( mappedBy = "memberEntity" ) // 1:N PK쪽에 사용하는 어노테이션 , mappedBy="fk필드명"
    @Builder.Default // 빌더 사용 시 해당 필드의 초기값 설정
    private List<BoardEntity> boardEntityList = new ArrayList<>();

    @Column // 회원등급
    private String role;


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
