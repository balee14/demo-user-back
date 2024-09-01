package com.terry.demo.domain.member.core.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.terry.demo.core.dto.CommonResponseEntity;
import com.terry.demo.core.dto.CommonResponseEntityType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Log4j2
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
        AccessDeniedException accessDeniedException) throws IOException, ServletException {

        ResponseEntity<?> responseEntity = new ResponseEntity<>(new CommonResponseEntity<>(
            CommonResponseEntityType.FORBIDDEN, accessDeniedException.toString())
            , CommonResponseEntityType.FORBIDDEN.getHttpStatus());

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonLogout = objectMapper.writeValueAsString(responseEntity.getBody());

        // application/json
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(jsonLogout);


    }



}
