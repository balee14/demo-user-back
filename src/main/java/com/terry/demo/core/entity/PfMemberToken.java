package com.terry.demo.core.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@ToString
@Table(name = "pf_member_token")
@Comment("JWT TOKEN관리")
public class PfMemberToken extends PfBaseEntity {

    @Comment("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id", unique = true, nullable = false)
    private Long tokenId;

    @Comment("사용자 idEmail")
    @Column(name = "id_email", nullable = false)
    private String idEmail;

    @Comment("토큰 사용 여부(true: 사용, false: 미사용)")
    @Column(name = "is_use", columnDefinition = "boolean default true", nullable = false)
    private Boolean isUse;

    @Comment("토큰")
    @Column(name="access_token", length = 250, nullable = false)
    private String accessToken;

    @Comment("토큰 수정일")
    @CreatedDate
    @ColumnDefault("current_timestamp()")
    @Column(name = "access_token_dt", updatable = false, nullable = false)
    //@Column(name = "REG_DT", columnDefinition = "timestamp default CURRENT_TIMESTAMP not null comment '최초 등록 일자' ")
    private LocalDateTime accessTokenDt;

    @Comment("리플리쉬 토큰")
    @Column(name="refresh_token", length = 250, nullable = false)
    private String refreshToken;

    @Comment("리플리쉬 토큰 수정일")
    @CreatedDate
    @ColumnDefault("current_timestamp()")
    @Column(name = "refresh_token_dt", updatable = false, nullable = false)
    private LocalDateTime refreshTokenDt;



}
