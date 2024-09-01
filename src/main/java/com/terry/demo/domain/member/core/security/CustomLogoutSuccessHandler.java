package com.terry.demo.domain.member.core.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.terry.demo.core.dto.CommonCustomResponse;
import com.terry.demo.core.dto.CommonCustomType;
import com.terry.demo.core.dto.CommonResponseEntity;
import com.terry.demo.core.dto.CommonResponseEntityType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        CommonCustomResponse commonCustomResponse = new CommonCustomResponse();
        commonCustomResponse.setCustomCode(CommonCustomType.getCustomCodeFindAny(CommonCustomType.SUCCESSFUL_200_1.name()));
        commonCustomResponse.setCustomMessage(CommonCustomType.getCustomMessageFindAny(CommonCustomType.SUCCESSFUL_200_1.name()));
        ResponseEntity<?> responseEntity = new ResponseEntity<>(new CommonResponseEntity<>(CommonResponseEntityType.OK, commonCustomResponse)
            , CommonResponseEntityType.OK.getHttpStatus());

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonLogout = objectMapper.writeValueAsString(responseEntity.getBody());

        // application/json
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(jsonLogout);

    }

}
