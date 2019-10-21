package com.leien.entity;

import lombok.Data;

/**
 * @Compny:LeiEnChuanMei
 * @Author: SSF
 * @Date: 2019/9/24 11:37
 * @Description:设备返回数据实体类
 */
@Data
public class DeviceReturnData {

    private String id;
    /**
     * 设备类型(0=温控器，1=风机)
     */
    private String deviceType;
    /**
     * 进水温度
     */
    private double inletTemperature;
    /**
     * 回水温度
     */
    private double reWaterTemperature;
    /**
     * 阀门状态(0：关，1：开)
     */
    private String valveStatus;

    /**
     * 备一温度
     */
    private double beiYiTemperature;
    /**
     * 备二温度
     */
    private double  beiErTemperature;
    /**
     * 添加时间
     */
    private String addTime;
    /**
     * 设备UUID
     */
    private String deviceUuid;

    /**
     * 设备名称
     */
    private String name;

    /**
     * 设备状态(0：在线，1：离线)
     */
    private Integer zhaungtai;

    /**
     * 设备状态名称
     */
    private String zhuangtaiName;

    /**
     * 风机状态(1：开，0：关)
     */
    private String fanStatus;

}
