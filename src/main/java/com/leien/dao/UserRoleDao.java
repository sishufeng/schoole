package com.leien.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Compny:LeiEnChuanMei
 * @Auther: SSF
 * @Date: 2019/9/26 13:46
 * @Description:用户权限映射类
 */
public interface UserRoleDao {
    /**
     * 新增用户权限
     * @param roleId
     * @param userId
     * @return
     */
    Integer addUserRole(@Param("roleId") Integer roleId, @Param("userId") Integer userId);

    /**
     * 删除用户权限
     * @param userId
     * @return
     */
    Integer deleteUserRoleByUserId(List userId);
}
