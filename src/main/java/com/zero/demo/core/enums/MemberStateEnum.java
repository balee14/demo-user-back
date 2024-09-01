package com.zero.demo.core.enums;


import com.terry.demo.core.config.enumeration.EnumMapperType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum MemberStateEnum implements EnumMapperType {
    //회원 사용여부
    Y( "사용"),
    N( "미사용"),
    D("폐기");

    @Getter
    private final String memberStateTitle;

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getTitle() {
        return memberStateTitle;
    }


    public static MemberStateEnum getMemberStateCodeFindAny(String memberStateCode) {

        return Arrays.stream(MemberStateEnum.values())
                .filter(v -> v.name().equals(memberStateCode))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("사용자 유형에 %s가 존재 하지 않습니다.", memberStateCode)));

    }

    public static MemberStateEnum getMemberStateTypeNameFindAny(String memberStateTitle) {

        return Arrays.stream(MemberStateEnum.values())
                .filter(v -> v.getMemberStateTitle().equals(memberStateTitle))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("사용자 유형에 %s가 존재 하지 않습니다.", memberStateTitle)));

    }

}
