package com.Ezenweb.domain.dto;

import lombok.*;

@NoArgsConstructor  // 빈생성자
@AllArgsConstructor // 풀생성자
@Getter @Setter     // 게터세터
@ToString           // 투스트링
@Builder            // 자율생성자
public class BoardDto {

    private String btitle;
    private String bcontent;


}
