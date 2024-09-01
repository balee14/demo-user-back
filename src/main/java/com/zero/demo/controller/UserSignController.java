package com.zero.demo.controller;

import com.terry.demo.service.sign.UserSignService;
import com.terry.demo.sign.dto.SigninRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/user/v1")
@RequiredArgsConstructor
public class UserSignController {

    /**
     * 로그인, 로그아웃 및 토큰관리
     */

    private final UserSignService userSignService;

    /**
     * 로그인
     *
     * @param
     * @return
     */
    @PostMapping("/sign/in")
    public ResponseEntity<?> userSignIn(@RequestBody SigninRequest signinRequest) {
        return userSignService.getUserSignIn(signinRequest);
    }

    /**
     * accessToken 재발급
     *
     * @param
     * @return
     */
    @PostMapping("/sign/accesstoken")
    public ResponseEntity<?> userAccesstoken(HttpServletRequest request, HttpServletResponse response) {
        return userSignService.getUserAccesstoken(request, response);
    }

    /**
     * todo : get이 아닌 post방식으로 처리 할 것(특별한 경우를 제외하곤 사용하지 않는다.)
     * 로그아웃
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/sign/logout")
    public ResponseEntity<?> userSignLogout(HttpServletRequest request, HttpServletResponse response){
        return userSignService.getUserSignLogout(request, response);

    }



}
