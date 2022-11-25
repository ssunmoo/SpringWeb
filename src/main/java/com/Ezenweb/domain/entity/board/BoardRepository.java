package com.Ezenweb.domain.entity.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository // 엔티티를 조작할 수 있는 리모컨 역할의 인터페이스
public interface BoardRepository extends JpaRepository< BoardEntity, Integer > {
                                               // 조작할 것의 클래스명, PK의 자료형

    // 1. 제목 검색
    @Query( value = "select * from board where bcno = :bcno and btitle like %:keyword%", nativeQuery = true )
    Page<BoardEntity> findBybtitle( int bcno, String keyword, Pageable pageable );

    // 2. 내용 검색
    @Query( value = "select * from board where bcno = :bcno and bcontent like %:keyword%", nativeQuery = true )
    Page<BoardEntity> findBybcontent( int bcno, String keyword, Pageable pageable );

    // 2. 검색이 없을 때
    @Query( value = "select * from board where bcno = :bcno", nativeQuery = true )
    Page<BoardEntity> findBybcno( @Param("bcno") int bcno, Pageable pageable );




    // 1. 기본 메소드 외 메소드 추가
        // 1. .findById( pk값 ) : 해당 pk의 엔티티 하나 호출
        // 2. .findAll() : 모든 엔티티 호출
        // 3. 직접 find 만들기 : .findBy필드명(조건) Optional<엔티티명>, List<엔티티명>, 엔티티명
        // 4. 페이징처리 : .findBy필드명(조건, Pageable pageable)

    // @Query : ( value = "쿼리문", nativeQuery = true )
    // SQL 변수 넣기
    // [인수] ( @Param("변수명") 자료형 변수명 ) ---> : 변수명    // @Param 생략가능
    // [인수] ( 자료형 변수명, 자료형 변수명 )    ---> : ?인수번호


//    @Query( value = "select * from board where bcno = :bcno", nativeQuery = true )
//    Page< BoardEntity > findBybcno(int bcno, Pageable pageable );
//    // 페이징처리 때문에 Page 사용

//    @Query( value = "select p from board p where p.bcno = ?1")
//    Page< BoardEntity > findBybcno(int bcno, Pageable pageable );






}
