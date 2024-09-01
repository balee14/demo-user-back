-- pf_authority definition
SET foreign_key_checks = 0;    # 외래키 체크 설정 해제
SET foreign_key_checks = 1;    # 외래키 체크 설정

DROP TABLE pf_authority;

CREATE TABLE `pf_authority` (
                                `authority_name` varchar(50) NOT NULL COMMENT '권한명',
                                `reg_dt` datetime(6) NOT NULL DEFAULT current_timestamp(6) COMMENT '등록일',
                                `reg_id` varchar(250) NOT NULL DEFAULT 'anonymousUser' COMMENT '등록자',
                                `mod_dt` datetime(6) NOT NULL DEFAULT current_timestamp(6) COMMENT '수정일',
                                `mod_id` varchar(250) NOT NULL DEFAULT 'anonymousUser' COMMENT '수정자',
                                PRIMARY KEY (`authority_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='권한';

INSERT INTO pf_authority
(authority_name, reg_dt, reg_id, mod_dt, mod_id)
VALUES('ROLE_ADMIN', '2024-03-13 17:01:17.294', 'admin', '2024-03-13 17:01:17.294', 'admin')
    , ('ROLE_USER', '2024-03-13 17:01:17.294', 'admin', '2024-03-13 17:01:17.294', 'admin')  ;
