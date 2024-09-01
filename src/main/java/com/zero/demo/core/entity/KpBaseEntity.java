package com.zero.demo.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass // 객체의 입장에서 공통 매핑 정보가 필요할 때 사용
@Getter
@Setter
public abstract class KpBaseEntity {

    @Comment("등록일")
    @CreatedDate
    @ColumnDefault("current_timestamp()")
    @Column(name = "reg_dt", updatable = false, nullable = false)
    private LocalDateTime regDt;

    @Comment("등록자")
    @CreatedBy
    @ColumnDefault("'anonymousUser'")
    @Column(name = "reg_id", length = 250, updatable = false, nullable = false)
    private String regId;

    @Comment("수정일")
    @LastModifiedDate
    @ColumnDefault("current_timestamp()")
    @Column(name = "mod_dt", nullable = false)
    private LocalDateTime modDt;

    @Comment("수정자")
    @LastModifiedBy
    @ColumnDefault("'anonymousUser'")
    @Column(name = "mod_id", length = 250, nullable = false)
    private String modId;

}
