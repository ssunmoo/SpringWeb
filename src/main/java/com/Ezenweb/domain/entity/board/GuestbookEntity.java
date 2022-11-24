package com.Ezenweb.domain.entity.board;

import com.Ezenweb.domain.dto.GuestbookDto;
import com.Ezenweb.domain.entity.bcategory.GuestbookCgEntity;
import lombok.*;

import javax.persistence.*;

@Table(name = "guestbook")  // 테이블 명 정의
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
public class GuestbookEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int gbno;

    @Column( nullable = false )
    private String gbcontent;

    @Column( nullable = false )
    private String gbname;

    private String gbfile;  // 첨부파일

        // 연관관계
        @ManyToOne
        @JoinColumn(name = "gbcno") // db 명시 필드
        @ToString.Exclude
        private GuestbookCgEntity guestbookCgEntity;



        public GuestbookDto toGuestbookDto() { // 뷰에서 입력 받은내용
        return GuestbookDto.builder()
                .gbno( this.gbno )
                .gbcontent( this.gbcontent )
                .gbname( this.gbname )
                .build();
    }

}
