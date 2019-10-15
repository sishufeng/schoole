package com.leien.entity;

import lombok.Data;

/**
 * @Compny:LeiEnChuanMei
 * @Author: SSF
 * @Date: 2019/9/26 13:44
 * @Description:用户权限实体类
 */
@Data
public class UserRole {
    private Integer id;
    private Integer userId;
    private Integer roleId;
}
