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
    private Integer id;
    /**
     * 设备ID
     */
    private Integer deviceId;
    /**
     * 设备类型ID
     */
    private Integer deviceTypeId;
    /**
     * 设备温度
     */
    private Double temperature;
    /**
     * 设备湿度
     */
    private Double humidity;
}
