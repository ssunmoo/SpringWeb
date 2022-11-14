package com.Ezenweb.domain.entity;

import lombok.*;

import javax.persistence.*;

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
    private int mno;
    private String memail;
    private String mpw;


    // 2. 생성자

    // 3. 메소드





}
