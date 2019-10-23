package com.leien.service.impl;

import com.leien.dao.DeviceDao;
import com.leien.entity.Device;
import com.leien.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

/**
 * @Compny:LeiEnChuanMei
 * @Author: SSF
 * @Date: 2019/9/24 12:18
 * @Description:设备业务实现类
 */
@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Override
    public List<Device> findAll() {
        return deviceDao.findAll();
    }

    /**
     * 根据设备UUID查询设备信息
     * @param uuidList
     * @return
     */
    @Override
    public List<String> queryDeviceByUuid(List<String> uuidList) {

        return deviceDao.queryDeviceByUuid(uuidList);
    }

    /**
     * 如果设备离线根据设备UUID查询设备信息
     * @param deviceUUID
     * @return
     */
    @Override
    public List<Device> queryDevicesByUuids(String deviceUUID) {

        return deviceDao.queryDevicesByUuids(deviceUUID);
    }

    /**
     * 批量插入设备信息，事务控制。
     * @param deviceList
     * @return
     */
    @Override
    @Transactional
    public int bulkInsertDevice(List<Device> deviceList) {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(definition);
        try{
            int insertDeviceCount = deviceDao.bulkInsertDevice(deviceList);
            return insertDeviceCount;
        }catch (Exception ex){
            transactionManager.rollback(status);
            throw ex;
        }
    }

    /**
     * 单条插入设备信息
     * @param device
     * @return
     */
    @Override
    public int insertDevice(Device device) {

        return deviceDao.insertDevice(device);
    }

    /**
     * 根据设备UUID，删除设备
     * @param deviUuid
     * @return
     */
    @Override
    public int deleteDevice(String deviUuid) {
        return deviceDao.deleteDevice(deviUuid);
    }

    /**
     * 删除所有设备信息
     * @return
     */
    @Override
    public int deleteDevicesAll() {
        return deviceDao.deleteDevicesAll();
    }

    /**
     * 根据设备UUID查询设备信息(设备不在线时)
     * @param deviceUuid
     * @return
     */
    @Override
    public Device queryDeviceByUuidDesc(String deviceUuid) {
        return deviceDao.queryDeviceByUuidDesc(deviceUuid);
    }

    /**
     * 远程接口服务故障时，查询本地数据页面展示
     * @return
     */
    @Override
    public Device queryDevicesForServiceFailure() {
        return deviceDao.queryDevicesForServiceFailure();
    }
}
