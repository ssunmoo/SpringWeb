package com.Ezenweb.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter @Setter
@ToString
@MappedSuperclass // 상속받을 경우 자식 클래스에게 매핑 정보 전달
@EntityListeners( AuditingEntityListener.class )
public class BaseEntity {

    @CreatedDate // 데이터 생성 날짜를 자동 주입
    @Column(updatable = false ) // 수정 불가
    private LocalDateTime cdate; // cdate 필드명

    @LastModifiedDate // 데이터 수정 날짜를 자동 주입
    private LocalDateTime udate; // udate 필드명
}
