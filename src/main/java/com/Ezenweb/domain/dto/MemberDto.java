package com.Ezenweb.domain.dto;

import com.Ezenweb.domain.entity.MemberEntity;
import lombok.*;

import javax.persistence.Column;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
@Builder
public class MemberDto {

    private int mno;
    private String memail;
    private String mpw;
    private String mphone;      // 회원 전화번호 필드


    // Dto --> Entity 로 변환
    public MemberEntity toEntity() {
        return MemberEntity.builder() // 빌더 메소드 시작
                .mno( this.mno )
                .memail( this.memail )
                .mpw( this.mpw )
                .mphone( this.mphone)
                .build(); // 빌더 메소드 끝
    }


}
