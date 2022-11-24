package com.Ezenweb.domain.dto;

import com.Ezenweb.domain.entity.board.GuestbookEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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
    private MultipartFile gbfile;


    public GuestbookEntity toGuestbookEntity(){ // 실제 저장할 내용
        return GuestbookEntity.builder()
               .gbno(this.gbno)
               .gbcontent(this.gbcontent)
               .gbname(this.gbname)
               .build();
    }
}
