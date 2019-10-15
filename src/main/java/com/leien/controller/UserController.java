package com.leien.controller;

import com.leien.entity.User;
import com.leien.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Compny:LeiEnChuanMei
 * @Author: SSF
 * @Date: 2019/9/17 10:32
 * @Description:用户处理类
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 分页查询所有用户
      * @return
     */
    @CrossOrigin
    @GetMapping("/queryList")
    public Map<String,Object> queryList(User user) {
        Map<String,Object> map = new HashMap<>();
        List<User> userEntities = userService.queryAllUser(user);
        map.put("code","0");
        map.put("msg","");
        map.put("data",userEntities);
        map.put("count",userService.count());
        return map;
    }

    /**
     * 查看用户详情
     * @return
     */
    @GetMapping("/details")
    public User queryUserDetails(Integer userId) {
        User user = userService.queryUserById(userId);
        return user;
    }

    /**
     * 跳转到登陆页
     */
    @PostMapping("/login")
    public Map<String,Object> login(String phone, String password, HttpSession session) {
        Map<String,Object> map = new HashMap<>();
        User user = userService.queryUserByPhone(phone);
        if (null != user){
            session.setAttribute("user",user);
            map.put("data",user);
        }else {
            map.put("nouser","该用户不存在或密码错误");
        }
        return map;
    }

    /**
     * 修改用户登录密码
     * @return
     */
    @PostMapping("/updatePwd")
    public Map<String,Object> editUserLoginPwd(String userPwd,HttpSession session){
        User user = (User) session.getAttribute("user");
        Map<String, Object> resultMap = userService.updateLoginPwdByUserId(userPwd,user.getUserId());
        return resultMap;
    }

    /**
     * 添加新用户
     * @param user
     * @return
     */
    @PostMapping("/add")
    public Map<String,Object> saveUser(User user){
        //新用户密码默认为 123456
        user.setPassWord("123456");
        Map<String, Object> map = userService.saveUser(user);
        return map;
    }

    /**
     * 修改用户详情
     * @param user
     * @return
     */
    @PostMapping("/saveEditUser")
    public Map<String,Object> updateUserDetails(User user){
        Map<String, Object> map = userService.updateUserDetailsByUserId(user);
        return map;
    }

    /**
     * 删除用户(可以批量删除)
     * @param userId
     * @return
     */
    @PostMapping("/deleteUser")
    public Map<String,Object> deleteUserByUSerId(String userId){
        List userIdList = new ArrayList();
        String[] userIds = userId.split(",");
        for (int i = 0; i < userIds.length; i++){
                userIdList.add(userIds[i]);
        }
        Map<String, Object> resultMap = userService.deleteUserByUserId(userIdList);
        return resultMap;
    }
}
