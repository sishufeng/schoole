package com.leien.service;

import com.leien.entity.Device;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Compny:LeiEnChuanMei
 * @Author: SSF
 * @Date: 2019/9/24 12:17
 * @Description:设备业务类
 */
public interface DeviceService {
    /**
     * 查询所有设备
     * @return
     */
    List<Device> findAll();

    /**
     * 根据设备UUID查询设备
     * @param deviceUUID
     * @return
     */
    int queryDeviceByUuid(String deviceUUID);

    /**
     * 保存设备信息
     * @param deviceList
     * @return
     */
    int bulkInsertDevice(@Param("deviceList") List<Device> deviceList);
}
