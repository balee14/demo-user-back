package com.terry.demo.user.controller;


import com.terry.demo.core.util.PfPropertyUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserIndexController {

    /**
     * user INDEX
     * @param res
     * @throws Exception
     */
    @GetMapping(value = "/")
    public void userIndex(HttpServletResponse res) throws Exception {
        res.setContentType("text/html");
        res.setCharacterEncoding("utf-8");
        res.getWriter().print("USER-" + PfPropertyUtil.getProperty("spring.profiles.active"));
    }

}
