package com.Ezenweb.domain.dto;

import com.Ezenweb.domain.entity.board.BoardEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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
    private MultipartFile bfile;// 첨부파일 객체 [ 업로드용 ]
    private String bfilename;   // 첨부파일 [ 출력용 ]
    private int mno;            // 작성자 회원번호 FK
    private int bcno;           // 카테고리 FK
    private String memail;      // 회원 아이디

    // 페이징 처리
    private int startbtn;
    private int endbtn;



    public BoardEntity toEntity() {
        // * 생성자를 사용한 객체 생성
        return BoardEntity.builder()
                .bno( this.bno )
                .btitle( this.btitle )
                .bcontent( this.bcontent)
                .bview( this.bview )
                .build();
    }


}
