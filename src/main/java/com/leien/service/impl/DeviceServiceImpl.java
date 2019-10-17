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
     * 根据设备UUID查询设备是否存在
     * @param deviceUUID
     * @return
     */
    @Override
    public int queryDeviceByUuid(String deviceUUID) {

        return deviceDao.queryDeviceByUuid(deviceUUID);
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
        definition.setName("");
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
}
