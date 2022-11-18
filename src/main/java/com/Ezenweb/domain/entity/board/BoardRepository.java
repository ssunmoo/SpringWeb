package com.Ezenweb.domain.entity.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // 엔티티를 조작할 수 있는 리모컨 역할의 인터페이스
public interface BoardRepository extends JpaRepository< BoardEntity, Integer > {
                                               // 조작할 것의 클래스명, PK의 자료형
}
