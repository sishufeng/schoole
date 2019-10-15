package com.leien.dao;

import com.leien.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Compny:LeiEnChuanMei
 * @Author: SSF
 * @Date: 2019/9/20 09:40
 * @Description:
 */
//@Mapper
public interface UserDao {
    /**
     * 登录时按手机号码查询用户
     */
    User queryUserByPhone(String phone);

    /**
     * 根据用户ID查询用户权限
     * @param userId
     * @return
     */
    String queryUserRoleByUserId(Integer userId);

    /**
     * 根据用户Id查询用户详情
     * @return
     */
    User queryUserById(Integer userId);

    /**
     * 根据用户Id删除用户
     * @return
     */
    Integer deleteUserByUserId(List userId);

    /**
     * 保存新增用户
     * @return
     */
    Integer saveUser(User user);

    /**
     * 分页查询所有用户
     * @return
     */
    List<User> queryAllUser(@Param("page") Integer page ,@Param("size") Integer size);

    /**
     * 查询用户列表总数量
     * @return
     */
    long count();

    /**
     * 根据用户Id修改登陆密码
     * @return
     */
    Integer updateLoginPwdByUserId(@Param("userPwd") String userPwd,@Param("userId") Integer userId);

    /**
     * 根据用户ID更改用户信息
     * @param user
     * @return
     */
    Integer updateUserByUserId(User user);
}
