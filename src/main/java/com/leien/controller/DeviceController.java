package com.leien.controller;

import com.leien.entity.Device;
import com.leien.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Compny:LeiEnChuanMei
 * @Auther: SSF
 * @Date: 2019/9/24 13:55
 * @Description:设备控制处理类
 */

@RestController
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping("/device/getDevice")
    public List<Device> queryAllDevice(){
        List<Device> device = deviceService.findAll();
        return device;
    }
}
