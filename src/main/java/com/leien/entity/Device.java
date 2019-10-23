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
public class Device extends DeviceReturnData{

    /**
     * 主键
     */
    private String uuid;

    /**
     * 设备类型名称
     */
    private String typeName;


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
     * 设备状态(0：离线，1：在线)
     */
    private Integer zhaungtai;

    /**
     * 设备状态名称
     */
    private String deviceZhuangTaiName;

    /**
     * 设备说明
     */
    private String remarks;
    /**
     * 设备类型(7：温控，8：风机)
     */
    private Integer deviceTypes;

}
