package com.leien.applicationrunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.leien.entity.Device;
import com.leien.service.DeviceService;
import com.leien.utils.APIUtil;
import com.leien.utils.UUIDUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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

    public List pageList = new ArrayList();
    public List dataList = new ArrayList();
    @Autowired
    APIUtil apiUtil;
    @Autowired
    private DeviceService deviceService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Device> list = new ArrayList<>();
        //获取token
        String token = apiUtil.getToken();
        if (StringUtils.isEmpty(token)){
            token = apiUtil.getToken();
        }
        //获取设备信息
        String deviceInformation = apiUtil.getDeviceInformation(token);
        if (!StringUtils.isEmpty(deviceInformation)) {
            JSONArray objects = JSON.parseArray(deviceInformation);

            for (int i = 0; i < objects.size(); i++) {
                Device device = new Device();
                JSONObject object = (JSONObject) objects.get(i);
                String typeName = object.getString("type_name");
                String name = object.getString("name");
                String zhaungtai = object.getString("zhaungtai");
                String uuid = object.getString("uuid");
                String remarks = object.getString("remarks");
                /*if (!StringUtils.isEmpty(name) && name.equals("风机")){
                    device.setDeviceType();
                }*/
                device.setTypeName(typeName);
                device.setDeviceName(name);
                //设备状态(0：在线，1：不在线)
                if (!StringUtils.isEmpty(zhaungtai) && zhaungtai.equals("0")){
                    device.setZhaungtai("在线");
                }else {
                    device.setZhaungtai("不在线");
                }

                device.setUuid(uuid);
                device.setRemarks(remarks);
                device.setId(UUIDUtils.getUuid());
                list.add(device);
            }
            deviceService.bulkInsertDevice(list);
        }
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());
        String finalToken = token;
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                //do something
                if (!StringUtils.isEmpty(finalToken)) {
                    String returnData = apiUtil.getData(finalToken);
                    pageList.add(returnData);
                    if (pageList.size() == 50) {
                        deviceService.bulkInsertDevice(pageList);
                    }
//                    count++;
////                    if (count == 60){//3分钟往数据库保存一次
////                        dataList.add(returnData);
////                        System.out.println(">>>>>>>>>>>>++++++++>>>>>>"+returnData);
////                        count = 0;
////                    }
                }
            }
        },3000,3000, TimeUnit.MILLISECONDS);

    }
}
