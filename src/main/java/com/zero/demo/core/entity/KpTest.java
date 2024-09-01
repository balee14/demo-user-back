package com.zero.demo.core.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.web.multipart.MultipartFile;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "kp_test")
@Comment("테스트")
public class KpTest extends KpBaseEntity {

    @Comment("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_id", unique = true, nullable = false)
    private Long testId;

    @Comment("사용자ID")
    @Column(name = "id_email", unique = true, nullable = false, length = 250) //사용자 ID
    private String idEmail;

    @Transient
    private MultipartFile multipartFile;


}
