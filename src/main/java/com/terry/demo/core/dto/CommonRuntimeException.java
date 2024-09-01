package com.terry.demo.core.dto;

public class CommonRuntimeException extends RuntimeException {

    public CommonRuntimeException(String errorCode) {
        super(errorCode);
    }

}
