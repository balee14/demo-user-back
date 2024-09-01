package com.terry.demo.core.converter;


import com.terry.demo.core.enums.MemberStateEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

@Convert
@Slf4j
public class MemberStateConverter implements AttributeConverter<MemberStateEnum, String > {

    @Override
    public String convertToDatabaseColumn(MemberStateEnum memberState) {

        if (memberState == null) return null;

        return memberState.name();

    }

    @Override
    public MemberStateEnum convertToEntityAttribute(String dbData) {

        if(ObjectUtils.isEmpty(dbData)) return null;

        try {
            return MemberStateEnum.getMemberStateCodeFindAny(dbData);
        } catch (IllegalArgumentException illegalArgumentException) {
            log.error("failure to convert cause unexpected code [{}]", dbData, illegalArgumentException);
            throw illegalArgumentException;
        }

    }

}
