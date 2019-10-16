package com.leien.service.impl;

import com.leien.dao.UserDao;
import com.leien.dao.UserRoleDao;
import com.leien.entity.User;
import com.leien.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Compny:LeiEnChuanMei
 * @Author: SSF
 * @Date: 2019/9/20 12:00
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {


    @Resource
    private UserDao userDao;

    @Resource
    private UserRoleDao userRoleDao;

    /**
     * 根据用户手机号查询用户
     * @param phone
     * @return
     */
    @Override
    public User queryUserByPhone(String phone) {
        User user = userDao.queryUserByPhone(phone);

        return user;
    }

    /**
     * 根据用户ID查询用户角色
     * @param userId
     * @return
     */
    @Override
    public String queryUserRoleByUserId(Integer userId) {

        return userDao.queryUserRoleByUserId(userId);
    }

    /**
     * 根据用户Id查询用户详情
     * @param userId
     * @return
     */
    @Override
    public User queryUserById(Integer userId) {
        return userDao.queryUserById(userId);
    }

    /**
     * 保存添加的用户
     * @param user
     * @return
     */
    @Override
    public Map<String,Object> saveUser(User user,String userName,String phone) {
        Map<String,Object> map = new HashMap<>();
        String inspect=this.jianchaUserName(userName);
        Integer ph =this.jianchaPhone(phone);
        if(inspect==null||inspect.equals("")){
            if(ph>0){
                map.put("msg","手机号重复");
            }else {
                Integer num = userDao.saveUser(user);
                if (num > 0){
                    map.put("msg","添加成功");
                    //添加成功再查询该用户信息，获取用户ID
                    User user1 = userDao.queryUserByPhone(user.getPhone());
                    //把用户ID和权限ID保存到用户权限关联表中
                    userRoleDao.addUserRole(user.getRoleId(),user1.getUserId());
                }else {
                    map.put("msg","添加失败");
                }
            }
        }else {
            map.put("msg","用户名重复");
        }
        return map;
    }
    /**
     * 查看添加的用户名是否重复
     * @param userName
     * @return
     */
    @Override
    public String jianchaUserName(String userName){
        return userDao.jianCha(userName);
    }
    /**
     * 查看添加的用户名是否重复
     * @param phone
     * @return
     */

    @Override
    public Integer jianchaPhone(String phone){

        return userDao.jianchaPhone(phone);
    }
    /**
     * 根据用户Id修改用户信息
     * @param user
     * @return
     */
    @Override
    public Map<String,Object> updateUserDetailsByUserId(User user) {
        Map<String,Object> map = new HashMap<>();
        Integer num = userDao.updateUserByUserId(user);
        if (num > 0){
            map.put("msg","修改成功");
        }else {
            map.put("msg","修改失败");
        }
        return map;
    }

    /**
     * 根据用户Id删除用户信息
     * @param userId
     * @return
     */
    @Override
    public Map<String,Object> deleteUserByUserId(List userId) {
        Map<String,Object> map = new HashMap<>();

        Integer deleteNum = userDao.deleteUserByUserId(userId);
        if (deleteNum > 0){
            map.put("msg","删除成功");
            //删除用户同时删除用户权限表中该用户的权限
            Integer deleteUserRole = userRoleDao.deleteUserRoleByUserId(userId);
            if (deleteUserRole > 0){
                map.put("roleMsg","删除权限成功");
            }else {
                map.put("roleMsg","删除权限失败");
            }
        }else {
            map.put("msg","删除失败");
        }
        return map;
    }

    /**
     * 修改用户登录密码
     * @param userId
     * @return
     */
    @Override
    public Map<String,Object> updateLoginPwdByUserId(String userPwd,Integer userId) {
        Map<String,Object> map = new HashMap<>();
        Integer num = userDao.updateLoginPwdByUserId(userPwd,userId);
        if (num > 0){
            map.put("msg","修改成功");
        }else {
            map.put("msg","修改失败");
        }
        return map;
    }

    /**
     * 查询用户列表
     * @return
     */
    @Override
    public List<User> queryAllUser(User user) {
        return userDao.queryAllUser(user.getPage(),user.getLimit());
    }

    /**
     * 查询用户总条数
     * @return
     */
    @Override
    public long count() {
        return userDao.count();
    }
}
