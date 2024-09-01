-- pf_member_token definition
DROP TABLE pf_member_token;

CREATE TABLE `pf_member_token` (
                                   `token_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                   `id_email` varchar(255) NOT NULL COMMENT '사용자 idEmail',
                                   `is_use` tinyint(1) NOT NULL DEFAULT 1 COMMENT '토큰 사용 여부(true: 사용, false: 미사용)',
                                   `access_token` varchar(250) NOT NULL COMMENT '토큰',
                                   `access_token_dt` datetime(6) NOT NULL DEFAULT current_timestamp(6) COMMENT '토큰 수정일',
                                   `refresh_token` varchar(250) NOT NULL COMMENT '리플리쉬 토큰',
                                   `refresh_token_dt` datetime(6) NOT NULL DEFAULT current_timestamp(6) COMMENT '리플리쉬 토큰 수정일',
                                   `reg_dt` datetime(6) NOT NULL DEFAULT current_timestamp(6) COMMENT '등록일',
                                   `reg_id` varchar(250) NOT NULL DEFAULT 'anonymousUser' COMMENT '등록자',
                                   `mod_dt` datetime(6) NOT NULL DEFAULT current_timestamp(6) COMMENT '수정일',
                                   `mod_id` varchar(250) NOT NULL DEFAULT 'anonymousUser' COMMENT '수정자',
                                   PRIMARY KEY (`token_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='JWT TOKEN관리';
