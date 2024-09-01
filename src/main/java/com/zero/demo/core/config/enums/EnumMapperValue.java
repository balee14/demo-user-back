package com.zero.demo.core.config.enums;

import lombok.ToString;

@ToString
public class EnumMapperValue {

    private String code;
    private String title;

    public EnumMapperValue(EnumMapperType enumMapperType) {
        code = enumMapperType.getCode();
        title = enumMapperType.getTitle();
    }

    public String getValue() { return code;}

    public String getLabel() { return title;}


}
