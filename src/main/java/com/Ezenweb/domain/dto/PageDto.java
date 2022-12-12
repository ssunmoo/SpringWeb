package com.Ezenweb.domain.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PageDto {

    private int bcno; // 카테고리
    private int page; // 현재페이지
    private String key; // 검색 필드
    private String keyword; // 검색 단어

    @Builder.Default // 빌더 사용 시 현재 객체가 기본적으로 할당
    private List<BoardDto> list = new ArrayList<BoardDto>(); // 검색된 결과 리트스
    private int startbtn; // 시작 페이지
    private int endbtn; // 마지막 페이지
    private Long totalBoards; // 총게시물수 .getTotalElements() 메소드가 반환타입 LONG


}
