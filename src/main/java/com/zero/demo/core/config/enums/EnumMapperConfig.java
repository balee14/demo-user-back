package com.zero.demo.core.config.enums;

import com.terry.demo.core.enumeration.MemberStateEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnumMapperConfig {

    @Bean
    public EnumMapper enumMapper() {

        EnumMapper enumMapper = new EnumMapper();
        //사용하는 Enum 등록하기
        enumMapper.put("MemberStateEnum", MemberStateEnum.class);
        return enumMapper;

    }
}
