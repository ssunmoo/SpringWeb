package com.Ezenweb.domain.dto;

import com.Ezenweb.domain.entity.board.GuestbookEntity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class GuestbookDto {

    private int gbno;
    private String gbcontent;
    private String gbname;
    private int gbcno; // 카테고리 FK


    public GuestbookEntity toGuestbookEntity(){ // 실제 저장할 내용
        return GuestbookEntity.builder()
               .gbno(this.gbno)
               .gbcontent(this.gbcontent)
               .gbname(this.gbname)
               .build();
    }
}
