package com.Ezenweb.domain.entity;

import com.Ezenweb.domain.dto.BoardDto;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity                  // 엔티티 정의
@Table(name = "board")  // 테이블 명 정의
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Builder
@ToString
public class BoardEntity {

    // 1. 필드
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY) // auto increment
    private int bno;            // 게시글번호

    private String btitle;      // 제목

    @Column(nullable = false, columnDefinition = "TEXT" ) // 자료형을 text로 변경
    private String bcontent;    // 내용

    @Column(nullable = false)
    @ColumnDefault("0")         // JPA가 insert할 경우 default 값 넣기
    private int bview;          // 조회수

    @Column(nullable = false)
    private String bfile;       // 첨부파일

    @Column(nullable = false)
    private int mno;            // 작성자 회원번호 FK

    @Column(nullable = false)
    private int cno;            // 카테고리 FK





    public BoardDto toDto(){
        // * 빌더 패턴을 이용한 객체 생성
        return BoardDto.builder()
                .bno( this.bno )
                .btitle( this.btitle )
                .bcontent( this.bcontent)
                .bview( this.bview )
                .bfile( this.bfile )
                .mno( this.mno )
                .cno( this.cno )
                .build();
    }

    
}
