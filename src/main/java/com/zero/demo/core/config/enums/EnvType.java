package com.zero.demo.core.config.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * environment
 */
@Getter
@RequiredArgsConstructor
public enum EnvType {

    LOCAL("local", false),
    DEV("dev", true),
    STAGE("stage", true),
    PROD("prod", true);

    private final String envTypeNm;

    private final boolean isJwtType;

//    EnvType(String envTypeNm, boolean isCertificationType) {
//        this.envTypeNm = envTypeNm;
//        this.isCertificationType = isCertificationType;
//    }

    public static boolean isJwtType(String envTypeNm) {
        return Arrays.stream(EnvType.values())
                .filter(v -> v.getEnvTypeNm().equalsIgnoreCase(envTypeNm))
                .findAny()
                .orElse(LOCAL).isJwtType();
    }

}
