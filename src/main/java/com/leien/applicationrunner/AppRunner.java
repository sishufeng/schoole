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
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                //获取远程设备信息
                String deviceInformation = apiUtil.getDeviceInformation(token);
                List<DeviceReturnData> deviceReDataList = JSON.parseArray(deviceInformation, DeviceReturnData.class);
                deviceData = getDeviceData(deviceReDataList);
                //保存到数据库
                insertDevice(deviceInformation);
            }
        }, 1000, 10000, TimeUnit.MILLISECONDS);
    }


    /**
     * 获取设备信息
     * @param list
     * @return
     */
    public Map<String, List> getDeviceData(List<DeviceReturnData> list){
        List pageList = new ArrayList();
        Map<String, List> deviceListMap = new HashMap<>();
        DeviceReturnData returnData1;
        for(DeviceReturnData deviceReturnData : list){
            returnData1 = new DeviceReturnData();
            returnData1.setName(deviceReturnData.getName());
            //设备状态(0：离线，1：在线)
            int deviceSta = deviceReturnData.getZhaungtai();
            returnData1.setZhaungtai(deviceSta);
            if (deviceSta == 1){
                returnData1.setZhuangtaiName("在线");
                deviceListMap = onLineDevice(returnData1);
                //温控在线设备
                List deviceList = deviceListMap.get("device");
                deviceListMap.put("temperatureControl",deviceList);
                //风机在线设备
                List fanList = deviceListMap.get("fan");
                deviceListMap.put("fanControl",fanList);
            }else {
                returnData1.setZhuangtaiName("离线");
                returnData1.setInletTemperature(0.0);
                returnData1.setReWaterTemperature(0.0);
                returnData1.setBeiYiTemperature(0.0);
                returnData1.setBeiErTemperature(0.0);
                returnData1.setValveStatus(String.valueOf(0));
                pageList.add(returnData1);
                deviceListMap.put("offLine",pageList);
            }
        }
        return deviceListMap;
    }

    /**
     * 设备在线，处理方法
     */
    public Map<String,List> onLineDevice(DeviceReturnData returnData1){
        List deviceList = new ArrayList();
        List fanList = new ArrayList();
        Map<String,List> map = new HashMap<>();
//        List<DeviceReturnData> deviceReturnDataList = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String returnData = apiUtil.getData(token);
            JSONObject jsonObject = JSON.parseObject(returnData);
            String deviceKey = jsonObject.getString("data");
            List<JsonRootBean> deviceData = JSON.parseArray(deviceKey, JsonRootBean.class);
            // 如果deviceData.size()>0时所有设备都在线,解析设备信息
            if(deviceData.size()>0){
                for (JsonRootBean jsonBeen : deviceData){
                    String msg = jsonBeen.getMsg();
                    String clientUuid = jsonBeen.getClientUuid();
                    String[] s = HexUtils.subStringData(msg);
                    returnData1.setId(UUIDUtils.getUuid());
                    returnData1.setDeviceUuid(clientUuid);
                    //设备工作类型  0=温控器，1=风机
                    int deviceStatus = Integer.parseInt(s[3] + s[4],16);
                    // 温控解析
                    if(deviceStatus == 0){
                        returnData1.setDeviceType("温控器");
                        // 设备阀门状态(0：关，1：开)
                        int valveSta = Integer.parseInt(s[15] + s[16],16);
                        if(valveSta == 1){
                            returnData1.setValveStatus("开");
                        }else {
                            returnData1.setValveStatus("关");
                        }
                        //冷水温度
                        returnData1.setInletTemperature(Long.parseLong(s[17] + s[18],16));
                        // 内管温度
                        returnData1.setReWaterTemperature(Long.parseLong(s[19] + s[20],16));
                        // 外管温度
                        returnData1.setBeiYiTemperature(Long.parseLong(s[21] + s[22],16));
                        // 集热温度
                        returnData1.setBeiErTemperature(Long.parseLong(s[23] + s[24],16));
                        returnData1.setAddTime(format.format(jsonBeen.getAddTime()));
                        deviceList.add(returnData1);
                        map.put("device",deviceList);
                    }else {
                        //风机解析
                        returnData1.setDeviceType("风机");
                        //设备工作类型  0=温控器，1=风机
                        int deviceSta = Integer.parseInt(s[3] + s[4],16);
                        // 风机状态(1：开，0：关)
                        int fanStatus = Integer.parseInt(s[15] + s[16],16);
                        if(fanStatus == 1){
                            returnData1.setFanStatus("开");
                        }else {
                            returnData1.setFanStatus("关");
                        }
                        returnData1.setAddTime(format.format(jsonBeen.getAddTime()));
                        fanList.add(returnData1);
                        map.put("fan",fanList);
                    }
                    String isRead = s[1];
                    //根据 addTime,设备UUID 查询数据库是否已有该条数据
                    DeviceReturnData deviceReData = deviceReService.queryDeviceDataByAddTime(format.format(jsonBeen.getAddTime()),clientUuid);
                    // 返回数据第三位代表操作类型，03：读取，06：操作。并且时间不同再往数据库保存
                    if (isRead.equals("03") && deviceReData == null){
                        deviceReService.insertDeviceReData(returnData1);
//                        deviceReturnDataList.add(returnData1);
                    }
//                    if(deviceReturnDataList.size() % 2 == 0){
//                        deviceReService.bulkInsertDevice(deviceReturnDataList);
//                    }
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
            List deviceList = getDeviceList(deviceData);
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
            device.setTypeName(typeName);
            device.setDeviceName(name);
            //设备状态(0：离线，1：在线)
            if (!StringUtils.isEmpty(zhaungtai) && zhaungtai.equals("0")){
                device.setZhaungtai("离线");
            }else {
                device.setZhaungtai("在线");
            }
            device.setUuid(uuid);
            device.setRemarks(remarks);
            list.add(device);
        }
        return list;
    }

}
