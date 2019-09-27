package com.leien.service;

import com.leien.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @Compny:LeiEnChuanMei
 * @Auther: SSF
 * @Date: 2019/9/20 11:59
 * @Description:
 */
public interface UserService {
    /**
     * 根据手机号码查询用户
     * @param phone
     * @return
     */
    User queryUserByPhone(String phone);

    /**
     * 根据用户ID查询用户权限
     * @param userId
     * @return
     */
    String queryUserRoleByUserId(Integer userId);
    /**
     * 根据用户Id查询用户
     * @return
     */
    User queryUserById(Integer userId);

    /**
     * 添加用户
     * @return
     */
    Map<String,Object> saveUser(User user);

    /**
     * 根据用户Id修改用户
     * @return
     */
    Map<String,Object> updateUserDetailsByUserId(User user);

    /**
     * 根据用户Id删除用户
     * @return
     */
    Map<String,Object> deleteUserByUserId(List userId);

    /**
     * 根据用户Id修改登陆密码
     * @return
     */
    Map<String,Object> updateLoginPwdByUserId(String userPwd,Integer userId);

    /**
     * 查询用户列表
     * @return
     */
    List<User> queryAllUser(User user);

    /**
     * 查询总条数
     * @return
     */
    long count();

}
