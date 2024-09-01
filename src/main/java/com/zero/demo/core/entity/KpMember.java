package com.zero.demo.core.entity;

import com.terry.demo.core.converter.MemberStateConverter;
import com.terry.demo.core.enumeration.MemberStateEnum;
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
@Table(name = "kp_member")
@Comment("회원")
public class KpMember extends KpBaseEntity {

    @Comment("ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", unique = true, nullable = false)
    private Long memberId;

    @Comment("회원 ID")
    @Column(name = "id_email", unique = true, nullable = false, length = 250)
    private String idEmail;

    @Comment("회원 PW")
    //@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    @Column( length = 250, nullable = false)
    private String pwd;

    @Comment("사용여부(사용:Y, 미사용:N)")
    @Column(length = 1, nullable = false)
    @Convert(converter = MemberStateConverter.class)
    private MemberStateEnum state;

    @Comment("회원명")
    @Column(length = 250, nullable = false)
    private String name;

    @Comment("회원 연락처")
    @Column(length = 15, nullable = false)
    private String telephone;

    @Comment("회원 이메일")
    @Column(length = 250)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "kp_member_authority",
        joinColumns = {@JoinColumn(name = "member_id", referencedColumnName = "member_id")},
        inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")})
    private Set<KpAuthority> authorities;

}

