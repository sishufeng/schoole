package com.leien.dao;

import com.leien.entity.Device;

import java.util.List;

/**
 * @Compny:LeiEnChuanMei
 * @Author: SSF
 * @Date: 2019/9/24 11:49
 * @Description:设备数据映射类
 */
public interface DeviceDao {
    /**
     * 查询所有设备
     * @return
     */
    List<Device> findAll();
}
