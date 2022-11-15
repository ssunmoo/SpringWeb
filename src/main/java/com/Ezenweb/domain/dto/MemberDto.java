package com.Ezenweb.domain.dto;

import com.Ezenweb.domain.entity.MemberEntity;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
@Builder
public class MemberDto {

    private int mno;
    private String memail;
    private String mpw;

    // Dto --> Entity 로 변환
    public MemberEntity toEntity() {

        return MemberEntity.builder()
                .memail( this.memail )
                .mpw( this.mpw )
                .build();

    }


}
