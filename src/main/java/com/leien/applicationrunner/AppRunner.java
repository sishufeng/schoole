package com.leien.applicationrunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leien.entity.Device;
import com.leien.entity.DeviceReturnData;
import com.leien.entity.JsonRootBean;
import com.leien.service.DeviceReturnDataService;
import com.leien.service.DeviceService;
import com.leien.utils.APIUtil;
import com.leien.utils.HexUtils;
import com.leien.utils.UUIDUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Compny:LeiEnChuanMei
 * @Author: SSF
 * @Date: 2019/10/9 11:57
 * @Description: SpringBoot项目启动时自动执行指定方法
 */
@Component
@Order(value = 1)
public class AppRunner implements ApplicationRunner {

    private final static Logger logger = LoggerFactory.getLogger(AppRunner.class);

    public String token ;
    /**
     * 返给页面数据
     */
    public Map<String, List> deviceData;

    /**
     * 注入连接远程接口工具类
     */
    @Autowired
    APIUtil apiUtil;

    /**
     * 注入设备接口类
     */
    @Resource
    private DeviceService deviceService;
    /**
     * 注入设备返回数据接口类
     */
    @Autowired
    private DeviceReturnDataService deviceReService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        //获取token
        token = apiUtil.getToken();
        if (StringUtils.isEmpty(token)){
            token = apiUtil.getToken();
        }
        //获取远程设备信息
        String deviceInformation = apiUtil.getDeviceInformation(token);

        if(deviceInformation != null && !StringUtils.isEmpty(deviceInformation)){
            //保存到数据库
            insertDevice(deviceInformation);
        }
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                String devices = apiUtil.getDeviceInformation(token);
                List<Device> deviceReDataList = JSON.parseArray(devices, Device.class);
                deviceData = getDeviceData(deviceReDataList);
            }
        }, 1000, 10000, TimeUnit.MILLISECONDS);
    }


    /**
     * 获取设备信息
     * @param list
     * @return
     */
    public Map<String, List> getDeviceData(List<Device> list){
        List pageList = new ArrayList();
        Map<String, List> deviceListMap = new HashMap<>();
        Device returnData1;
        for(Device device : list){
            returnData1 = new Device();
            String schooluuid = device.getProjectUuid();

            returnData1.setName(device.getName());
            returnData1.setProjectName(device.getProjectName());
            returnData1.setProjectUuid(device.getProjectUuid());
            //设备状态(0：离线，1：在线)
            int deviceSta = device.getZhaungtai();
            returnData1.setZhaungtai(deviceSta);
            if (deviceSta == 1){
                returnData1.setDeviceZhuangTaiName("在线");
                deviceListMap = onLineDevice(returnData1);
                //温控在线设备
                List deviceList = deviceListMap.get("device");
                deviceListMap.put("temperatureControl",deviceList);
                //风机在线设备
                List fanList = deviceListMap.get("fan");
                deviceListMap.put("fanControl",fanList);
            }else {
                returnData1.setDeviceZhuangTaiName("离线");
                returnData1.setUuid(device.getUuid());
                returnData1.setTypeId(device.getTypeId());
                pageList.add(returnData1);
                deviceListMap.put("offLine",pageList);
            }
        }
        return deviceListMap;
    }

    /**
     * 设备在线，处理方法
     */
    public Map<String,List> onLineDevice(Device returnData1){
        List deviceList = new ArrayList();
        List fanList = new ArrayList();
        Map<String,List> map = new HashMap<>();
        DeviceReturnData deviceReturnData = new DeviceReturnData();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String returnData = apiUtil.getData(token);
            JSONObject jsonObject = JSON.parseObject(returnData);
            String deviceKey = jsonObject.getString("data");
            List<JsonRootBean> deviceData = JSON.parseArray(deviceKey, JsonRootBean.class);
            // 如果deviceData.size()>0时所有设备都在线,解析设备信息
            if(deviceData.size()>0){
                for (JsonRootBean jsonBeen : deviceData){
                    deviceReturnData.setName(returnData1.getName());
                    deviceReturnData.setProjectName(returnData1.getProjectName());
                    deviceReturnData.setDeviceZhuangTaiName(returnData1.getDeviceZhuangTaiName());
                    String msg = jsonBeen.getMsg();
                    String clientUuid = jsonBeen.getClientUuid();
                    String[] s = HexUtils.subStringData(msg);
                    deviceReturnData.setId(UUIDUtils.getUuid());
                    deviceReturnData.setDeviceUuid(clientUuid);
                    //设备工作类型  0=温控器，1=风机
                    int deviceStatus = Integer.parseInt(s[3] + s[4],16);
                    // 温控解析
                    if(deviceStatus == 0){
                        deviceReturnData.setDeviceTypeName("温控器");
                        deviceReturnData.setDeviceType(deviceStatus);
                        // 设备阀门状态(0：关，1：开)
                        int valveSta = Integer.parseInt(s[15] + s[16],16);
                        if(valveSta == 1){
                            deviceReturnData.setValveStatus(valveSta);
                            deviceReturnData.setValveStatusName("开");
                        }else {
                            deviceReturnData.setValveStatus(valveSta);
                            deviceReturnData.setValveStatusName("关");
                        }
                        //冷水温度
                        deviceReturnData.setInletTemperature(Long.parseLong(s[17] + s[18],16));
                        // 内管温度
                        deviceReturnData.setReWaterTemperature(Long.parseLong(s[19] + s[20],16));
                        // 外管温度
                        deviceReturnData.setBeiYiTemperature(Long.parseLong(s[21] + s[22],16));
                        // 集热温度
                        deviceReturnData.setBeiErTemperature(Long.parseLong(s[23] + s[24],16));
                        deviceReturnData.setAddTime(format.format(jsonBeen.getAddTime()));
                        deviceList.add(deviceReturnData);
                        map.put("device",deviceList);
                    }else {
                        //风机解析
                        deviceReturnData.setDeviceTypeName("风机");
                        deviceReturnData.setDeviceType(deviceStatus);
                        //设备工作类型  0=温控器，1=风机
                        int deviceSta = Integer.parseInt(s[3] + s[4],16);
                        // 风机状态(1：开，0：关)
                        int fanStatus = Integer.parseInt(s[15] + s[16],16);
                        if(fanStatus == 1){
                            deviceReturnData.setFanStatus(fanStatus);
                            deviceReturnData.setFanStatusName("开");
                        }else {
                            deviceReturnData.setFanStatus(fanStatus);
                            deviceReturnData.setFanStatusName("关");
                        }
                        deviceReturnData.setAddTime(format.format(jsonBeen.getAddTime()));
                        fanList.add(deviceReturnData);
                        map.put("fan",fanList);
                    }
                    String isRead = s[1];
                    //根据 addTime,设备UUID 查询数据库是否已有该条数据
                    DeviceReturnData deviceReData = deviceReService.queryDeviceDataByAddTime(format.format(jsonBeen.getAddTime()),clientUuid);
                    // 返回数据第三位代表操作类型，03：读取，06：操作。并且时间不同再往数据库保存
                    if (isRead.equals("03") && deviceReData == null){
                        deviceReService.insertDeviceReData(deviceReturnData);
                    }
                }
            }

        return map;
    }

    /**
     * 获取设备信息保存到数据库
     * @param deviceData
     */
    public void insertDevice(String deviceData){
        if (!StringUtils.isEmpty(deviceData)) {
            // 插入之前先删除所有设备信息。保持数据和远程一致
            deviceService.deleteDevicesAll();
            //获取设备列表
            List deviceList = getDeviceList(deviceData);
            //批量插入数据库
            deviceService.bulkInsertDevice(deviceList);
        }
    }

    /**
     * 获取设备列表
     * @param data
     * @return
     */
    public List getDeviceList(String data){
        List<Device> list = new ArrayList<>();
        JSONArray objects = JSON.parseArray(data);
        for (int i = 0; i < objects.size(); i++) {
            Device device = new Device();
            JSONObject object = (JSONObject) objects.get(i);
            String typeName = object.getString("type_name");
            String name = object.getString("name");
            String zhaungtai = object.getString("zhaungtai");
            String uuid = object.getString("uuid");
            String remarks = object.getString("remarks");
            String deviceType = object.getString("type_id");
            device.setTypeId(Integer.valueOf(deviceType));
            device.setTypeName(typeName);
            device.setDeviceName(name);
            //设备状态(0：离线，1：在线)
            if (!StringUtils.isEmpty(zhaungtai) && "0".equals(zhaungtai)){
                device.setZhaungtai(Integer.valueOf(zhaungtai));
                device.setDeviceZhuangTaiName("离线");
            }else {
                device.setZhaungtai(Integer.valueOf(zhaungtai));
                device.setDeviceZhuangTaiName("在线");
            }
            device.setProjectUuid(object.getString("project_uuid"));
            device.setProjectName(object.getString("project_name"));
            device.setUuid(uuid);
            device.setRemarks(remarks);
            list.add(device);
        }
        return list;
    }

}
