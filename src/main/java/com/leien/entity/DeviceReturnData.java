package com.leien.entity;

/**
 * @Compny:LeiEnChuanMei
 * @Auther: SSF
 * @Date: 2019/9/24 11:37
 * @Description:设备返回数据实体类
 */
public class DeviceReturnData {
    private Integer id;
    private Integer deviceId;//设备ID
    private Integer deviceTypeId;//设备类型ID
    private Double temperature;//设备温度
    private Double humidity;//	设备湿度

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(Integer deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }
}
