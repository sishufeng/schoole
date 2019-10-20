package com.leien.controller;

import com.leien.applicationrunner.AppRunner;
import com.leien.entity.Device;
import com.leien.service.DeviceService;
import com.leien.utils.APIUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Compny:LeiEnChuanMei
 * @Author: SSF
 * @Date: 2019/9/24 13:55
 * @Description:设备控制处理类
 */

@RestController
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    APIUtil apiUtil;
    @Autowired
    AppRunner appRunner;

    @GetMapping("/getDevice")
    public Map<String,Object> queryAllDevice(){
        Map<String,Object> map = new HashMap<>();
        List<Device> device = deviceService.findAll();
        for(Device devicelist :device){
            if(devicelist.getDeviceType().equals("0")){
                devicelist.setDeviceType("温控器");
            }
            else {
                devicelist.setDeviceType("风机");
            }
            if(devicelist.getDeviceStatus().equals("0")){
                devicelist.setDeviceStatus("关");
            }
            else {
                devicelist.setDeviceStatus("开");
            }
            if(devicelist.getValveStatus().equals("0")){
                devicelist.setValveStatus("关");
            }
            else {
                devicelist.setValveStatus("开");
            }
           System.out.println(map);
        }
//        int deviceStatus=device.get(deviceStatus);
        map.put("code","0");
        map.put("msg","");
        map.put("data",device);
        return map;
    }


    /**
     * 获取设备信息
     * @return
     */
    @PostMapping("/getDeviceData")
    public Map<String,Object> getDeviceData(){
        List list = appRunner.pageList;
        Map<String,Object> map = new HashMap<>();
        map.put("code","0");
        map.put("msg","");
        map.put("data",list);
        return map;
    }

}
