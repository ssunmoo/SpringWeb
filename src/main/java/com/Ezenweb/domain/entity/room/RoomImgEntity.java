package com.Ezenweb.domain.entity.room;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "roomImg")
public class RoomImgEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int rimgno;
    private String rimg;


    // 룸 엔티티에게 pk 받기
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "rno")
    private RoomEntity roomEntity;


}
