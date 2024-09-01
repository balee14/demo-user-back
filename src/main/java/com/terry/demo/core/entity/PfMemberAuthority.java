package com.terry.demo.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.Comment;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pf_member_authority")
@Comment("회원 권한 매핑")
public class PfMemberAuthority extends PfBaseEntity {

    @Comment("id")
    @Id
    @Column(name = "member_id", length = 50)
    private Long memberId;

    @Comment("권한명")
    @Column(name = "authority_name", length = 50)
    private String authorityName;

}
