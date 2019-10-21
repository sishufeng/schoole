package com.leien.service.impl;

import com.leien.dao.DeviceReturnDataDao;
import com.leien.entity.DeviceReturnData;
import com.leien.service.DeviceReturnDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Compny:LeiEnChuanMei
 * @Author: SSF
 * @Date: 2019/10/18 15:01
 * @Description:
 * @ClassName: DeviceReturnDataServiceImpl
 */
@Service
public class DeviceReturnDataServiceImpl implements DeviceReturnDataService {
    @Autowired
    DeviceReturnDataDao returnDataDao;

    @Override
    public int bulkInsertDevice(List<DeviceReturnData> deviceList) {
        return 0;
    }

    @Override
    public int insertDeviceReData(DeviceReturnData deviceReturnData) {

        return returnDataDao.insertDeviceReData(deviceReturnData);
    }

    @Override
    public int insert(DeviceReturnData deviceReturnData) {
        return returnDataDao.insert(deviceReturnData);
    }

    /**
     * 查询设备返回数据的返回时间
     * @param addTime
     * @return
     */
    @Override
    public DeviceReturnData queryDeviceDataByAddTime(String addTime,String uuid) {
        return returnDataDao.queryDeviceDataByAddTime(addTime,uuid);
    }

    /**
     * 更新风机状态(1：开，0：关)
     * @param fanStatus
     * @return
     */
    @Override
    public int updateFanStatus(int fanStatus) {
        return returnDataDao.updateFanStatus(fanStatus);
    }
}
