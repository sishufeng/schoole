package com.leien.dao;

import com.leien.entity.DeviceReturnData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Compny:LeiEnChuanMei
 * @Author: SSF
 * @Date: 2019/10/18 15:02
 * @Description: 设备返回数据映射类
 * @ClassName: DeviceReturnDataDao
 */
public interface DeviceReturnDataDao {
    /**
     * 批量插入设备信息
     * @param deviceList
     * @return
     */
    int bulkInsertDevice(@Param("deviceList") List<DeviceReturnData> deviceList);

    /**
     * 单条插入设备信息
     * @param deviceReturnData
     * @return
     */
    int insertDeviceReData(DeviceReturnData deviceReturnData);

    int insert(DeviceReturnData deviceReturnData);

    /**
     * 查询远程设备返回数据的时间
     * @param addTime
     * @return
     */
    DeviceReturnData queryDeviceDataByAddTime(@Param("addTime") String addTime,@Param("uuid") String uuid);
}
