package com.leien.controller;

import com.leien.applicationrunner.AppRunner;
import com.leien.entity.Device;
import com.leien.service.DeviceService;
import com.leien.utils.APIUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @CrossOrigin
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
        map.put("code","0");
        map.put("msg","");
        map.put("data",device);
        return map;
    }


    /**
     * 获取温控设备信息
     * @return
     */
    @PostMapping("/getTemperatureControlData")
    public Map<String,Object> getTemperatureControlData(){
        Map<String,Object> map = new HashMap<>();
        Map<String, List> deviceData = appRunner.deviceData;
        List temperatureControlList = deviceData.get("temperatureControl");
        map.put("code","0");
        map.put("msg","");
        map.put("data",temperatureControlList);
        return map;
    }



    /**
     * 获取风机设备信息
     * @return
     */
    @PostMapping("/getFanControlData")
    public Map<String,Object> getFanControlData(){
        Map<String,Object> map = new HashMap<>();
        Map<String, List> deviceData = appRunner.deviceData;
        List fanControlList = deviceData.get("fanControl");
        map.put("code","0");
        map.put("msg","");
        map.put("data",fanControlList);
        return map;
    }

    /**
     * 刷新设备列表
     * @return
     */
    @PostMapping("/refreshDeviceList")
    public Map<String,Object> refreshDeviceList(){
        Map<String,Object> map = new HashMap<>();
        String data = apiUtil.getDeviceInformation(appRunner.token);
        if (!StringUtils.isEmpty(data)){
            List devicesList = appRunner.getDeviceList(data);
            map.put("data",devicesList);
        }
        return map;
    }
}
