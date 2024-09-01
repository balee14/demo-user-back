-- pf_test definition
DROP TABLE pf_test;

CREATE TABLE `pf_test` (
                           `test_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                           `id_email` varchar(250) NOT NULL COMMENT '사용자ID',
                           `reg_dt` datetime(6) NOT NULL DEFAULT current_timestamp(6) COMMENT '등록일',
                           `reg_id` varchar(250) NOT NULL DEFAULT 'anonymousUser' COMMENT '등록자',
                           `mod_dt` datetime(6) NOT NULL DEFAULT current_timestamp(6) COMMENT '수정일',
                           `mod_id` varchar(250) NOT NULL DEFAULT 'anonymousUser' COMMENT '수정자',
                           PRIMARY KEY (`test_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='테스트';
