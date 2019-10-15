package com.leien.controller;

import com.leien.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

/**
 * @Compny:LeiEnChuanMei
 * @Author: SSF
 * @Date: 2019/9/27 09:48
 * @Description: 主处理类
 */
@Controller
public class MainController {

    /**
     * 跳转到登陆页
     */
    @GetMapping("/login")
    public String toLogin() {
        return "login";
    }

    /**
     * 跳转到主页
     * @return
     */
    @GetMapping("/main")
    public String index(Model modelAndView, HttpSession session) {
        User user = (User) session.getAttribute("user");
        modelAndView.addAttribute("userInfo",user);
        return "index";
    }
}
