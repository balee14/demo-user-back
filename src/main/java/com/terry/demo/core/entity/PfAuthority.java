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
@Table(name = "pf_authority")
@Comment("권한")
public class PfAuthority extends PfBaseEntity {

    @Comment("권한명")
    @Id
    @Column(name = "authority_name", length = 50)
    private String authorityName;

}
