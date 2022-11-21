package com.Ezenweb.domain.dto;

import com.Ezenweb.domain.entity.board.BoardEntity;
import lombok.*;

@NoArgsConstructor  // 빈생성자
@AllArgsConstructor // 풀생성자
@Getter @Setter     // 게터세터
@ToString           // 투스트링
@Builder            // 자율생성자
public class BoardDto {

    private int bno;            // 게시글번호
    private String btitle;      // 제목
    private String bcontent;    // 내용
    private int bview;          // 조회수
    private String bfile;       // 파일
    private int mno;            // 작성자 회원번호 FK
    private int bcno;           // 카테고리 FK
    private String memail;      // 회원 아이디

    public BoardEntity toEntity() {
        // * 생성자를 사용한 객체 생성
        return BoardEntity.builder()
                .bno( this.bno )
                .btitle( this.btitle )
                .bcontent( this.bcontent)
                .bview( this.bview )
                .bfile( this.bfile )
                .build();
    }


}
