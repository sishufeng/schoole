package com.leien.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Compny:LeiEnChuanMei
 * @Author: SSF
 * @Date: 2019/10/28 16:57
 * @Description: 学校实体类
 * @ClassName: School
 */
@Data
public class School {
    /**
     * 主键
     */
    private int id;
    /**
     * 学校名称
     */
    private String schoolName;
    /**
     * 学校项目标识符
     */
    private String schoolProjectKey;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
}
