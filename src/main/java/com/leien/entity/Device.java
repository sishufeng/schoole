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
    private String id;
    /**
     * 设备类型ID
     */
    private Integer deviceTypeId;

    /**
     * 设备创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 设备说明
     */
    private String deviceRemark;
    /**
     * 设备编号
     */
    private Integer deviceNumber;
    /**
     * 设备状态
     */
    private Integer deviceStatus;
    /**
     * 访问接口使用
     */
    private String uuid;

}
