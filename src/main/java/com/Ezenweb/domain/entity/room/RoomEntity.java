package com.Ezenweb.domain.entity.room;

import com.Ezenweb.domain.entity.member.MemberEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "room")
public class RoomEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int rno;
    private String rtitle;
    private int rprice;
    private String rtrans;
    private String rname;
    private String rlat;
    private String rlng;

    // 멤버 엔티티에게서 pk 받기
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "mno")
    private MemberEntity memberEntity;


    // 룸 이미지 엔티티에게 pk 전달
    @OneToMany(mappedBy = "roomEntity")
    @ToString.Exclude
    @Builder.Default
    private List<RoomImgEntity> roomImgEntityList = new ArrayList<>();

}
