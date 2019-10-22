package com.leien.service;

import com.leien.entity.DeviceReturnData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Compny:LeiEnChuanMei
 * @Author: SSF
 * @Date: 2019/10/18 14:59
 * @Description: 设备返回数据业务接口类
 * @ClassName: DeviceReturnDataService
 */
public interface DeviceReturnDataService {
    /**
     * 批量插入设备信息
     * @param deviceList
     * @return
     */
    int bulkInsertDevice(@Param("deviceList") List<DeviceReturnData> deviceList);

    /**
     *  单条插入设备返回信息
     *  @param deviceReturnData
     * @return
     */
    int insertDeviceReData(DeviceReturnData deviceReturnData);

    int insert(DeviceReturnData deviceReturnData);

    /**
     * 查询设备返回数据的返回时间
     * @param addTime
     * @return
     */
    DeviceReturnData queryDeviceDataByAddTime(String addTime,String uuid);

    /**
     * 更新风机状态(1：开，0：关)
     * @param fanStatus
     * @return
     */
    int updateFanStatus(int fanStatus);
}
