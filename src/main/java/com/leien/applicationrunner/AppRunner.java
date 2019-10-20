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
import java.util.List;
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

    DeviceReturnData returnData1 ;
    String token ;
    public List pageList;
//    int count = 0;
    @Autowired
    APIUtil apiUtil;

    @Resource
    private DeviceService deviceService;
    @Autowired
    private DeviceReturnDataService deviceReService;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        //获取token
        token = apiUtil.getToken();
        if (StringUtils.isEmpty(token)){
            token = apiUtil.getToken();
        }else {
            //获取远程设备信息
            String deviceInformation = apiUtil.getDeviceInformation(token);
            //保存到数据库
            insertDevice(deviceInformation);
        }

        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                pageList = new ArrayList();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (!StringUtils.isEmpty(token)) {
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
                            returnData1 = new DeviceReturnData();
                            returnData1.setId(UUIDUtils.getUuid());
                            returnData1.setDeviceUuid(clientUuid);
                            //设备工作类型  0=温控器，1=风机
                            int deviceStatus = Integer.parseInt(s[4] + s[5],16);
                            if(deviceStatus == 0){
                                returnData1.setTypeName("温控器");
                            }else {
                                returnData1.setTypeName("风机");
                            }
                            // 温控解析
//                            if(deviceStatus == 0){
                                returnData1.setDeviceType(deviceStatus);
                                // 设备阀门状态(1：开，0：关)
                                int valveSta = Integer.parseInt(s[15] + s[16],16);
                                if(valveSta == 0){
                                }
                                returnData1.setValveStatus(valveSta);
                                //冷水温度
                                returnData1.setInletTemperature(Long.parseLong(s[17] + s[18],16));
                                // 内管温度
                                returnData1.setReWaterTemperature(Long.parseLong(s[19] + s[20],16));
                                // 外管温度
                                returnData1.setBeiYiTemperature(Long.parseLong(s[21] + s[22],16));
                                // 集热温度
                                returnData1.setBeiErTemperature(Long.parseLong(s[23] + s[24],16));
                                String addTime = format.format(jsonBeen.getAddTime());
                                returnData1.setAddTime(addTime);

                                pageList.add(returnData1);
                                //根据 addTime,设备UUID 查询数据库是否已有该条数据
                                DeviceReturnData deviceReData = deviceReService.queryDeviceDataByAddTime(addTime,clientUuid);
                                String isRead = s[1];
                                // 返回数据第三位代表操作类型，03：读取，06：操作。并且时间不同再往数据库保存
                                if (isRead.equals("03") && deviceReData == null){
                                    deviceReService.insertDeviceReData(returnData1);
                                }
//                            }else {
//                                //风机解析
//                            }
                        }
                    }
                }else {
                    token = apiUtil.getToken();
                }
//                count++;
            }

        }, 1000, 5000, TimeUnit.MILLISECONDS);
    }


    /**
     * 获取设备信息保存到数据库
     * @param deviceData
     */
    public void insertDevice(String deviceData){
        if (!StringUtils.isEmpty(deviceData)) {
            // 插入之前先删除所有设备信息。保持数据和远程一致
            deviceService.deleteDevicesAll();
            JSONArray objects = JSON.parseArray(deviceData);
            for (int i = 0; i < objects.size(); i++) {
                Device device = new Device();
                JSONObject object = (JSONObject) objects.get(i);
                String typeName = object.getString("type_name");
                String name = object.getString("name");
                String zhaungtai = object.getString("zhaungtai");
                String uuid = object.getString("uuid");
                String remarks = object.getString("remarks");
                if (!StringUtils.isEmpty(typeName) && typeName.equals("风机")){
                    device.setDeviceType("0");
                }else {
                    device.setDeviceType("1");
                }
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
                deviceService.insertDevice(device);
            }
        }
    }
}
