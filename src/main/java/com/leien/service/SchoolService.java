package com.leien.service;

import com.leien.entity.School;

import java.util.List;

/**
 * @Compny:LeiEnChuanMei
 * @Author: SSF
 * @Date: 2019/10/28 17:02
 * @Description: 学校业务类
 * @ClassName: SchoolService
 */
public interface SchoolService {
    /**
     * 查询所有学校项目标识符
     * @return
     */
    List<School> getSchoolProjectKey();
}
