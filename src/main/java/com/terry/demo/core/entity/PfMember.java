package com.terry.demo.core.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;

import java.util.Set;


@Entity
@Getter
@Setter
@ToString
@Table(name = "pf_member")
@Comment("회원")
public class PfMember extends PfBaseEntity {

    @Comment("ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", unique = true, nullable = false)
    private Long memberId;

    @Comment("사용자ID")
    @Column(name = "id_email", unique = true, nullable = false, length = 250)
    private String idEmail;

    @Comment("회원PW")
    @Column( length = 250, nullable = false)
    private String pwd;

    @Comment("회원명(nickname)")
    @Column(length = 250, nullable = false)
    private String name;

//    @Comment("사용여부(사용:Y, 미사용:N)")
//    @Column(length = 1, nullable = false)
//    @Convert(converter = MemberStateConverter.class)
//    private MemberStateEnum state;

//    @Comment("회원 연락처")
//    @Column(length = 15, nullable = false)
//    private String telephone;

//    @Comment("회원 이메일")
//    @Column(length = 250)
//    private String email;

//    @Comment("회원 상태(ex: 일반, 블랙컨슈머)")
//    @Column(length = 250)
//    @Convert(converter = MemberTypeConverter.class)
//    private MemberTypeEnum type;

//    @Comment("회원 등급")
//    @Column(name = "ranking")
//    @Convert(converter = MemberRankingConverter.class)
//    private MemberRankingEnum memberRanking;

//    @Comment("회원 가입구분 (ex: 이메일 가입)")
//    @Column(name="member_join_type", length = 250)
//    @Convert(converter = MemberJoinTypeConverter.class)
//    private MemberJoinTypeEnum memberJoinType;

//    @Comment("포인트")
//    @Column(length = 250, nullable = false)
//    @ColumnDefault("0")
//    private Long point;

//    @Comment("주문수")
//    @Column(name = "order_count", nullable = false)
//    @ColumnDefault("0")
//    private Long orderCount;

//    @Comment("리뷰수")
//    @Column(name = "review_count", nullable = false)
//    @ColumnDefault("0")
//    private Long reviewCount;

//    @Comment("약관동의")
//    @Column(name = "is_contract_agree", length = 1, columnDefinition = "boolean default false", nullable = false)
//    private Boolean isContractAgree;

//    @Comment("마케팅동의")
//    @Column(name = "is_marketing_agree", length = 1, columnDefinition = "boolean default false", nullable = false)
//    private Boolean isMarketingAgree;

//    @Comment("최종접속시간")
//    @LastModifiedDate
//    @ColumnDefault("current_timestamp()")
//    @Column(name = "last_connection", nullable = false)
//    private LocalDateTime lastConnection;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "pf_member_authority",
        joinColumns = {@JoinColumn(name = "member_id", referencedColumnName = "member_id")},
        inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<PfAuthority> authorities;

}

