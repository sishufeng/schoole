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
    private Integer deviceType;
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
    private Integer valveStatus;

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
     * 风机状态(1：开，0：关)
     */
    private Integer fanStatus;

    /**
     * 设备UUID
     */
    private String uuid;

    /**
     * 设备类型名称(0=温控器，1=风机)
     */
    private String deviceTypeName;

    /**
     * 阀门状态名称(0：关，1：开)
     */
    private String valveStatusName;
    /**
     * 风机状态(1：开，0：关)
     */
    private String fanStatusName;
    /**
     * 风机状态(1：开，0：关)
     */
    private String deviceZhuangTaiName;

    private String projectName;

}
