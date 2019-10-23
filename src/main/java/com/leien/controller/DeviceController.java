package com.leien.controller;

import com.leien.applicationrunner.AppRunner;
import com.leien.entity.DeviceReturnData;
import com.leien.service.DeviceService;
import com.leien.utils.APIUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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

    /**
     * 获取温控设备信息
     * @return
     */
    @CrossOrigin
    @PostMapping("/getTemperatureControlData")
    public Map<String,Object> getTemperatureControlData(){
        Map<String,Object> map = new HashMap<>();
        Map<String, List> deviceData = appRunner.deviceData;
        List temperatureControlList = deviceData.get("temperatureControl");
        // 在线
        if(temperatureControlList != null && temperatureControlList.size() >0){
            map.put("data",temperatureControlList);
        }else {
            //获取离线设备信息
            Map<String, List> temperatureControlMap = offLineDevices(deviceData);
            List temperatureControlDataOffLineList = temperatureControlMap.get("temperatureControlData");
            map.put("data",temperatureControlDataOffLineList);
        }
        map.put("code","0");
        map.put("msg","");
        return map;
    }

    /**
     * 获取风机设备信息
     * @return
     */
    @CrossOrigin
    @PostMapping("/getFanControlData")
    public Map<String,Object> getFanControlData(){

        Map<String,Object> map = new HashMap<>();
        Map<String, List> deviceData = appRunner.deviceData;
        List fanControlList = deviceData.get("fanControl");
        //在线
        if(fanControlList != null && fanControlList.size() >0){
            map.put("data",fanControlList);
        }else {
            //获取离线风机设备信息
            Map<String, List> fanControlMap = offLineDevices(deviceData);
            List fanControlDataOffLineList = fanControlMap.get("fanControlData");
            map.put("data",fanControlDataOffLineList);
        }
        map.put("code","0");
        map.put("msg","");
        return map;
    }

    /**
     * 刷新设备列表
     * @return
     */
    @CrossOrigin
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

    /**
     * 离线设备信息
     * @return
     */
    protected Map<String,List> offLineDevices(Map<String, List> deviceData){
        Map<String,List> map = new HashMap<>();
        DeviceReturnData temperatureControlData = new DeviceReturnData();
        DeviceReturnData fanControlData = new DeviceReturnData();
        List<DeviceReturnData> temperatureControlDataList = new ArrayList<>();
        List<DeviceReturnData> fanControlDataList = new ArrayList<>();
        List<DeviceReturnData> offLineDevices = deviceData.get("offLine");
        for (DeviceReturnData data : offLineDevices){
            String deviceName = data.getName();
            if(!StringUtils.isEmpty(deviceName) && "温度控制板".equals(deviceName)){
                temperatureControlData.setName(data.getName());
                temperatureControlData.setInletTemperature(data.getInletTemperature());
                temperatureControlData.setReWaterTemperature(data.getReWaterTemperature());
                temperatureControlData.setBeiYiTemperature(data.getBeiYiTemperature());
                temperatureControlData.setBeiErTemperature(data.getBeiErTemperature());
                temperatureControlData.setValveStatus(data.getValveStatus());
                temperatureControlData.setZhuangtaiName(data.getZhuangtaiName());
                temperatureControlData.setValveStatus("0");
                temperatureControlDataList.add(temperatureControlData);
            }else {
                fanControlData.setName(data.getName());
                fanControlData.setZhuangtaiName(data.getZhuangtaiName());
                fanControlData.setFanStatus(data.getFanStatus());
                fanControlDataList.add(fanControlData);
            }
        }
        map.put("temperatureControlData",temperatureControlDataList);
        map.put("fanControlData",fanControlDataList);
        return map;
    }
}
