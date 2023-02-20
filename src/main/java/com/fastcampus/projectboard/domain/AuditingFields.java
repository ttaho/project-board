package com.fastcampus.projectboard.domain;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)//Entity에서도 Auditing을 쓴다는 표시를 해줘야함.
@MappedSuperclass
public class AuditingFields {
    // 아래의 필드들은 자동으로 auditing해주기위해 아래와같은 애노테이션을 붙임
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    @Column(nullable = false, updatable = false) private LocalDateTime createdAt; //게시글 생성일시
    //위의 Date는 시스템시간을 하면된다고 치고, 밑의 게시글생성자는 어떻게 auditing을 해줄까?
    //JpaConfig에서 AuditorAware을 사용하여 잡아주는것 같다.
    @CreatedBy
    @Column(nullable = false, length=100, updatable = false) private String createdBy; //게시글 생성자

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate
    @Column(nullable = false)private LocalDateTime modifiedAt; //게시글 수정일시

    @LastModifiedBy
    @Column(nullable = false, length=100) private String modifiedBy; //게시글 수정자
}
