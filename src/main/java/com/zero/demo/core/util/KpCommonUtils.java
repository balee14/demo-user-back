package com.zero.demo.core.util;


import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class KpCommonUtils {

    /**
     * List<String> --> 인자에 따라서 Join 후 String으로 반환한다.
     * "userAuthority": [
     *             "ROLE_ADMIN"
     *             , "ROLE_PARTNER"
     *         ]
     * String str = ROLE_ADMIN, ROLE_PARTNER
     * @return
     */
    public static String listStringToString(List<String> securityList, String joining) {

        return String.join(joining, securityList);

    }

    /**
     * client ip를 획득한다.
     * @param request
     * @return
     */
    public static String getClientIp(HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        return ip;

    }

}
