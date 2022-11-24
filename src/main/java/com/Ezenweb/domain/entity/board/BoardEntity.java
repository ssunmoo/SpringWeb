package com.Ezenweb.domain.entity.board;

import com.Ezenweb.domain.dto.BoardDto;
import com.Ezenweb.domain.entity.BaseEntity;
import com.Ezenweb.domain.entity.bcategory.BcategoryEntity;
import com.Ezenweb.domain.entity.member.MemberEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;


@Table(name = "board")  // 테이블 명 정의
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
@ToString
@Entity // 엔티티 정의
public class BoardEntity extends BaseEntity {

    // 1. 필드
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)// auto increment
    private int bno;            // 게시글번호

    @Column(nullable = false)
    private String btitle;      // 제목

    @Column(nullable = false, columnDefinition = "TEXT" ) // 자료형을 text로 변경
    private String bcontent;    // 내용

    @Column(nullable = false)
    @ColumnDefault("0")         // JPA가 insert할 경우 default 값 넣기
    private int bview;          // 조회수

    private String bfile;       // 첨부파일

    // 연관관계1 [ 1:N FK에 해당하는 어노테이션 ] 회원번호 [PK] <-- 양방향 --> 게시물번호 [FK]
    @ManyToOne // PK[멤버]의 엔티티 객체
    @JoinColumn(name="mno") // 테이블에서 사용할 FK의 필드명
    @ToString.Exclude       // 해당 필드는 ToString을 사용하지 않는다 [ 양방향 시 필수 ]
    private MemberEntity memberEntity; // 작성자 회원번호 FK

    // 연관관계2 [ 카테고리번호 [PK] <-- 양방향 --> 게시물번호 [FK]
    @ManyToOne
    @JoinColumn(name="bcno")
    @ToString.Exclude
    private BcategoryEntity bcategoryEntity;


    public BoardDto toDto(){
        // * 빌더 패턴을 이용한 객체 생성
        return BoardDto.builder()
                .bno( this.bno )
                .btitle( this.btitle )
                .bcontent( this.bcontent)
                .bview( this.bview )
                .memail( this.memberEntity.getMemail() )
                .bfilename( this.bfile ) // 파일명만 함께 보내기
                .build();
    }
}
