package com.zero.demo.core.dto;

import com.terry.demo.core.util.KpDateUtil;
import lombok.Getter;

@Getter
public class CommonResponseEntity<T> {

    private final int responseCode;
    private final String responseTime;
    private T responseData;

    public CommonResponseEntity(CommonResponseEntityType commonResponseEntityType) {
        this.responseCode = commonResponseEntityType.getResponseCode();
        this.responseTime = KpDateUtil.getDateTime();
    }

    public CommonResponseEntity(CommonResponseEntityType commonResponseEntityType, T responseData) {
        this.responseCode = commonResponseEntityType.getResponseCode();
        this.responseTime = KpDateUtil.getDateTime();
        this.responseData = responseData;
    }

}
