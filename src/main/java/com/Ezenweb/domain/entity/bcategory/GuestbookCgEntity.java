package com.Ezenweb.domain.entity.bcategory;

import com.Ezenweb.domain.dto.GuestbookCgDto;
import com.Ezenweb.domain.entity.board.GuestbookEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "guestbookcg")  // 테이블 명 정의
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
public class GuestbookCgEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int gbcno;

    @Column( nullable = false )
    private String gbcname;

    // 연관관계
    @OneToMany( mappedBy = "guestbookCgEntity" ) // DB 처럼 쓰기 위해 매핑 // 연결할 엔티티의 private GuestbookCgEntity guestbookCgEntity; 랑 동일하게 작성
    @Builder.Default
    private List<GuestbookEntity> guestbookEntityList = new ArrayList<>();


    public GuestbookCgDto togbcDto(){
        return GuestbookCgDto.builder()
                .gbcno( this.gbcno )
                .gbcname( this.gbcname )
                .build();
    }

}
