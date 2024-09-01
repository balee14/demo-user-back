-- pf_member_authority definition
SET foreign_key_checks = 0;    # 외래키 체크 설정 해제
SET foreign_key_checks = 1;    # 외래키 체크 설정

DROP TABLE pf_member_authority;

CREATE TABLE `pf_member_authority` (
                                     `member_id` bigint(20) NOT NULL COMMENT 'id',
                                     `authority_name` varchar(50) NOT NULL COMMENT '권한명',
                                     `reg_dt` datetime(6) NOT NULL DEFAULT current_timestamp(6) COMMENT '등록일',
                                     `reg_id` varchar(250) NOT NULL DEFAULT 'anonymousUser' COMMENT '등록자',
                                     `mod_dt` datetime(6) NOT NULL DEFAULT current_timestamp(6) COMMENT '수정일',
                                     `mod_id` varchar(250) NOT NULL DEFAULT 'anonymousUser' COMMENT '수정자',
                                     PRIMARY KEY (`member_id`,`authority_name`),
                                     KEY `FK2bsrmyi9ygw5ldbrs9y9gdxoe` (`authority_name`),
                                     CONSTRAINT `FK2bsrmyi9ygw5ldbrs9y9gdxoe` FOREIGN KEY (`authority_name`) REFERENCES `pf_authority` (`authority_name`),
                                     CONSTRAINT `FKih8gok7f0v6ja1i2aewj3itf1` FOREIGN KEY (`member_id`) REFERENCES `pf_member` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='회원 권한 매핑';
