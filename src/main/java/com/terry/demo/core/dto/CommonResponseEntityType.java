package com.terry.demo.core.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonResponseEntityType {

    // Series.SUCCESSFUL
    OK(200, HttpStatus.OK, "OK"),

    // Series.CLIENT_ERROR
    BAD_REQUEST(400, HttpStatus.BAD_REQUEST, "Bad Request"),
    UNAUTHORIZED(401, HttpStatus.UNAUTHORIZED, "Unauthorized"),
    FORBIDDEN(403, HttpStatus.FORBIDDEN, "Forbidden"),
    NOT_FOUND(404, HttpStatus.NOT_FOUND, "Not Found"),

    // Series.SERVER_ERROR
    INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"),
    GATEWAY_TIMEOUT(504, HttpStatus.GATEWAY_TIMEOUT, "Gateway Timeout");

    private final int responseCode;
    private final HttpStatus httpStatus;
    private final String responseNote;

}
