package com.Ezenweb.domain.dto;

import com.Ezenweb.domain.entity.bcategory.GuestbookCgEntity;
import com.Ezenweb.domain.entity.board.GuestbookEntity;
import lombok.*;

import javax.persistence.Column;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class GuestbookCgDto {
    private int gbcno;
    private String gbcname;

    public GuestbookCgEntity togbcEntity(){
        return GuestbookCgEntity.builder()
               .gbcno( this.gbcno )
               .gbcname( this.gbcname )
               .build();
    }
}
