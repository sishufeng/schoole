package com.leien.service.impl;

import com.leien.dao.DeviceDao;
import com.leien.entity.Device;
import com.leien.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Compny:LeiEnChuanMei
 * @Auther: SSF
 * @Date: 2019/9/24 12:18
 * @Description:设备业务实现类
 */
@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceDao deviceDao;

    @Override
    public List<Device> findAll() {
        return deviceDao.findAll();
    }
}
