package com.leien.service;

import com.leien.entity.Device;

import java.util.List;

/**
 * @Compny:LeiEnChuanMei
 * @Auther: SSF
 * @Date: 2019/9/24 12:17
 * @Description:设备业务类
 */
public interface DeviceService {
    /**
     * 查询所有设备
     * @return
     */
    List<Device> findAll();
}
