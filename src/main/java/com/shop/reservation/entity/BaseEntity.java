package com.shop.reservation.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(value={AuditingEntityListener.class})
@DynamicInsert
@DynamicUpdate
public abstract class BaseEntity {

    // @Configuration 어노테이션을 받는 config 클래스에서 빈을 주입받아 현재 로그인한 사용자의 정보 매핑가능.
    @CreatedBy
    @Column(updatable = false)
    private Long createId; // 신규생성시 pk 자동증가로 인한 createId 입력불가로 우선 updatable=true

    @CreatedDate
    @Column(updatable = false)
    @JsonSerialize(using = LocalDateTimeSerializer.class) // 직렬화
    @JsonDeserialize(using = LocalDateTimeDeserializer.class) // 역직렬화
    @JsonFormat(pattern = "yyyy-MM-dd kk-mm:ss") // 원하는 형태의 LocalDateTime 설정
    private LocalDateTime createDate;

    @LastModifiedBy
    private Long updateId;

    @LastModifiedDate
    @JsonSerialize(using = LocalDateTimeSerializer.class) // 직렬화
    @JsonDeserialize(using = LocalDateTimeDeserializer.class) // 역직렬화
    @JsonFormat(pattern = "yyyy-MM-dd kk-mm:ss") // 원하는 형태의 LocalDateTime 설정
    private LocalDateTime updateDate;

}
