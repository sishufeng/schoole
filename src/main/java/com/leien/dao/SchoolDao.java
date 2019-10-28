package com.leien.dao;

import com.leien.entity.School;

import java.util.List;

/**
 * @Compny:LeiEnChuanMei
 * @Author: SSF
 * @Date: 2019/10/28 17:03
 * @Description: 学校数据映射类
 * @ClassName: SchoolDao
 */
public interface SchoolDao {

    /**
     * 查询所有学校项目标识符
     * @return
     */
    List<School> getSchoolProjectKey();
}
