package com.terry.demo.core.dto;

import com.terry.demo.core.util.PfDateUtil;
import lombok.Getter;

@Getter
public class CommonResponseEntity<T> {

    private final int responseCode;
    private final String responseTime;
    private T responseData;

    public CommonResponseEntity(CommonResponseEntityType commonResponseEntityType) {
        this.responseCode = commonResponseEntityType.getResponseCode();
        this.responseTime = PfDateUtil.getDateTime();
    }

    public CommonResponseEntity(CommonResponseEntityType commonResponseEntityType, T responseData) {
        this.responseCode = commonResponseEntityType.getResponseCode();
        this.responseTime = PfDateUtil.getDateTime();
        this.responseData = responseData;
    }

}
