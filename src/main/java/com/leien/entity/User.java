package com.leien.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.Objects;

/**
 * @Compny:LeiEnChuanMei
 * @Author: SSF
 * @Date: 2019/9/17 10:17
 * @Description:用户实体类
 */
@Data
public class User extends BaseEntity{

    private Integer userId ;
    private String roleName	;
    private String userName	;
    private String passWord	;
    private String phone ;
    private Integer roleId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    /**
     * 用户所属学校id
     */
    private Integer schoolId;
    /**
     * 用户所属学校名称
     */
    private Integer schoolName;

}
