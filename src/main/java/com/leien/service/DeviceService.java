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
     * @param uuidList
     * @return
     */
    List<String> queryDeviceByUuid(@Param("list") List<String> uuidList);
    /**
     * 如果设备离线根据设备UUID查询设备信息
     * @param deviceUUID
     * @return
     */
    List<Device> queryDevicesByUuids(String deviceUUID);

    /**
     * 批量插入设备信息
     * @param deviceList
     * @return
     */
    int bulkInsertDevice(@Param("deviceList") List<Device> deviceList);

    /**
     *  单条插入设备信息
     * @return
     */
    int insertDevice(Device device);

    /**
     * 根据设备UUID
     * @param deviUuid
     * @return
     */
    int deleteDevice(String deviUuid);

    /**
     * 删除所有设备信息
     * @return
     */
    int deleteDevicesAll();

    /**
     * 根据设备UUID查询设备信息(当服务不在线时)
     * @param deviceUuid
     * @return
     */
    Device queryDeviceByUuidDesc(String deviceUuid);

    /**
     * 远程接口服务故障时，查询本地数据页面展示
     * @return
     */
    Device queryDevicesForServiceFailure();

}
