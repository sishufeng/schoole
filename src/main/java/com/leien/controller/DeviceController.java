package com.leien.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.leien.applicationRunner.AppRunner;
import com.leien.entity.Device;
import com.leien.service.DeviceService;
import com.leien.utils.APIUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @Compny:LeiEnChuanMei
 * @Author: SSF
 * @Date: 2019/9/24 13:55
 * @Description:设备控制处理类
 */

//@RestController
    @Controller
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    APIUtil apiUtil;
    @Autowired
    AppRunner appRunner;

    @GetMapping("/getDevice")
    public List<Device> queryAllDevice(){
        List<Device> device = deviceService.findAll();
        return device;
    }

    /**
     * 获取设备信息
     * @return
     */
    @PostMapping("/getDeviceData")
    @ResponseBody
    public Map<String,Object> getDeviceData(){
        List list = appRunner.pageList;
        Map<String,Object> map = new HashMap<>();
        map.put("msg",list);
        return map;
    }


    @GetMapping("/go")
    public String device(){
        return "indexss";
    }
}
