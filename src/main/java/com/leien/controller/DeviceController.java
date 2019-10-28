package com.leien.controller;

import com.leien.applicationrunner.AppRunner;
import com.leien.entity.Device;
import com.leien.entity.DeviceReturnData;
import com.leien.service.DeviceService;
import com.leien.utils.APIUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
     * 获取温控在线设备信息
     * @return
     */
    @CrossOrigin
    @PostMapping("/getTemperatureControlData")
    public Map<String,Object> getTemperatureControlData(){
        Map<String,Object> map = new HashMap<>();
        Map<String, List> deviceData = appRunner.deviceData;
        if(deviceData != null && !deviceData.isEmpty()){
            List temperatureControlList = deviceData.get("temperatureControl");
            // 在线
//            if(temperatureControlList != null && temperatureControlList.size() >0){
                map.put("data",temperatureControlList);
//            }

        }else {
            //远程接口服务故障时获取本地数据返回页面展示
            Map<String, List> tMap = deviceData();
            List tData = tMap.get("tData");
            map.put("data",tData);
        }
        map.put("code","0");
        map.put("msg","");
        return map;
    }

    /**
     * 获取温控离线设备
     * @return
     */
    @CrossOrigin
    @PostMapping("/getOffLineTemperatureControlData")
    public Map<String,Object> getOffLineTemperatureControlData(){
        Map<String,Object> map = new HashMap<>();
        Map<String, List> deviceData = appRunner.deviceData;
        //获取离线温控设备信息
        Map<String, List> temperatureControlMap = offLineDevices(deviceData);
        if(temperatureControlMap != null && !temperatureControlMap.isEmpty()){
            //离线的温控设备
            List temperatureControlDataOffLineList = temperatureControlMap.get("temperatureControlData");
            //没有返回数据的离线的温控设备
            List temperatureControlDataOffLine = temperatureControlMap.get("offLineTemperatureControlData");
            map.put("offLineTemperatureControlNotData",temperatureControlDataOffLine);
            map.put("data",temperatureControlDataOffLineList);
        }
        map.put("code","0");
        map.put("msg","");
        return map;
    }

    /**
     * 获取风机在线设备信息
     * @return
     */
    @CrossOrigin
    @PostMapping("/getFanControlData")
    public Map<String,Object> getFanControlData(){
        Map<String,Object> map = new HashMap<>();
        Map<String, List> deviceData = appRunner.deviceData;
        //读取远程接口数据推送页面
        if(deviceData != null && !deviceData.isEmpty()){
            List fanControlList = deviceData.get("fanControl");
            //在线
//            if(fanControlList != null && fanControlList.size() >0){
                map.put("data",fanControlList);
//            }
        }else {
            //远程接口服务故障时获取本地数据返回页面展示
            Map<String, List> tMap = deviceData();
            List fData = tMap.get("fData");
            map.put("data",fData);
        }
        map.put("code","0");
        map.put("msg","");
        return map;
    }

    /**
     * 获取风机离线设备
     * @return
     */
    @CrossOrigin
    @PostMapping("/getOffLineFanControlData")
    public Map<String,Object> getOffLineFanControlData(){
        Map<String,Object> map = new HashMap<>();
        Map<String, List> deviceData = appRunner.deviceData;
        //获取离线风机设备信息
        Map<String, List> fanControlMap = offLineDevices(deviceData);
        if(fanControlMap != null && !fanControlMap.isEmpty()){
            //有返回数据的风机设备
            List fanControlDataOffLineList = fanControlMap.get("fanControlData");
            //没有返回数据的风机设备
            List fanControlDataOffLine = fanControlMap.get("offLineFanControlData");
            map.put("notReturnDataFan",fanControlDataOffLine);
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
            deviceService.deleteDevicesAll();
            int count = deviceService.bulkInsertDevice(devicesList);
            if(count > 0){
                map.put("msg","刷新成功！");
            }else {
                map.put("msg","刷新失败！");
            }
        }
        return map;
    }

    /**
     * 离线设备信息
     * @return
     */
    protected Map<String,List> offLineDevices(Map<String, List> deviceData){
        Map<String,List> map = new HashMap<>();
        List<Device> list = new ArrayList();
        List<Device> temperatureControlDataList = new ArrayList<>();
        List<Device> fanControlDataList = new ArrayList<>();
        Device offLineDevice;
        List<Device> offLineDevices = deviceData.get("offLine");
        for (DeviceReturnData data : offLineDevices){
            offLineDevice = new Device();
            //设备id
            String deviceUuid = data.getUuid();
            Device device = deviceService.queryDeviceByUuidDesc(deviceUuid);
            if(device != null){
                int deviceType = device.getDeviceType();
                //(0=温控器，1=风机)
                if( deviceType == 0){
                    temperatureControlDataList.add(device);
                }else {
                    fanControlDataList.add(device);
                }
            }else {
                offLineDevice = deviceService.queryDevicesByUuids(deviceUuid);
                list.add(offLineDevice);
            }
        }
        List temperatureControl = new ArrayList();
        List fanControlList = new ArrayList();
        for(Device device : list){
            // 8：风机， 7：温控
            if(device.getTypeId() == 8){
                //风机离线设备
                fanControlList.add(device);
            }else {
                //温控离线设备
                temperatureControl.add(device);
            }
        }
        //温控离线设备(再返回数据库中没有数据)
        map.put("offLineTemperatureControlData",temperatureControl);
        //风机离线设备(再返回数据库中没有数据)
        map.put("offLineFanControlData",fanControlList);
        //温控离线设备(再返回数据库中有数据)
        map.put("temperatureControlData",temperatureControlDataList);
        //风机离线设备(再返回数据库中有数据)
        map.put("fanControlData",fanControlDataList);
        return map;
    }

    /**
     * 远程接口服务故障时，查询本地数据页面展示
     * @return
     */
    protected Map<String,List> deviceData(){
        Map<String,List> map = new HashMap();
        List tList = new ArrayList();
        List fList = new ArrayList();
        //远程接口服务故障时，查询本地数据页面展示
        List<Device> devices = deviceService.findAll();
        for(Device device :devices){
            Device temperatureControlDevice = deviceService.queryDevicesForServiceFailure();
            if("温度控制板".equals(temperatureControlDevice.getDeviceName())){
                tList.add(temperatureControlDevice);
            }else {
                fList.add(temperatureControlDevice);
            }
        }
        map.put("tData",tList);
        map.put("fData",fList);
        return map;
    }
}
