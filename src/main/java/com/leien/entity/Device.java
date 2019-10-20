package com.leien.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Compny:LeiEnChuanMei
 * @Author: SSF
 * @Date: 2019/9/24 11:23
 * @Description:设备实体类
 */
@Data
public class Device {

    /**
     * 设备类型(0=温控器，1=风机)
     */
    private String deviceType;
    /**
     * 设备类型名称
     */
    private String typeName;

    /**
     * 阀门状态(0：关，1：开)
     */
    private String valveStatus;

    /**
     * 设备状态(0：关，1：开)
     */
    private String deviceStatus;

    /**
     * 进水温度
     */
    private double  inletTemperature;

    /**
     * 回水温度
     */
    private double  reWaterTemperature;

    /**
     * 设备湿度
     */
    private double  deviceHumidity;

    /**
     * 备一温度
     */
    private double  beiYiTemperature;

    /**
     * 备二温度
     */
    private double  beiErTemperature;

    /**
     * 设备创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     *  设备名称
     */
    private String  deviceName;
    /**
     * 设备状态(0：在线，1：不在线)
     */
    private String zhaungtai;

    /**
     * 设备说明
     */
    private String remarks;

    /**
     * 访问接口使用
     */
    private String uuid;

    /**
     * 设备所属学校
     */
    private String affiliatedSchool;

}
