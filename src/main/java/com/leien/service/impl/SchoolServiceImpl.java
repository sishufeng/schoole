package com.leien.service.impl;

import com.leien.dao.SchoolDao;
import com.leien.entity.School;
import com.leien.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Compny:LeiEnChuanMei
 * @Author: SSF
 * @Date: 2019/10/28 17:02
 * @Description: 学校业务处理类
 * @ClassName: SchoolServiceImpl
 */
@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    SchoolDao schoolDao;

    @Override
    public List<School> getSchoolProjectKey() {
        return schoolDao.getSchoolProjectKey();
    }
}
