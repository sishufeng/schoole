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
    @GetMapping("/index")
    public String index() {

        return "index";
    }
    @GetMapping("/ceshi")
    public String ceshi() {
        return "ceshi";
    }
    @GetMapping("/ChangePassword")
    public String ChangePassword() {
        return "ChangePassword";
    }
    @GetMapping("/adduser")
    public String adduser() {
        return "adduser";
    }
    @GetMapping("/userEdit")
    public String userEdit() {
        return "userEdit";
    }
    @GetMapping("/UserInformation")
    public String UserInformation() {
        return "UserInformation";
    }
    @GetMapping("/DeviceInformation")
    public String DeviceInformation() {
        return "DeviceInformation";
    }
    @GetMapping("/EChartsBing")
    public String EChartsBing() {
        return "EChartsBing";
    }
    @GetMapping("/EChartsZhu")
    public String EChartsZhu() {
        return "EChartsZhu";
    }
    @GetMapping("/EChartsZhe")
    public String EChartsZhe() {
        return "EChartsZhe";
    }
    @GetMapping("/schooleOne")
    public String schooleOne() {
        return "schooleOne";
    }
    @GetMapping("/schooleSecond")
    public String schooleSecond() {
        return "schooleSecond";
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
