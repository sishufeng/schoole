package com.leien.entity;
import java.util.Date;

/**
 * @Compny:LeiEnChuanMei
 * @Author: SSF
 * @Date: 2019/10/18 11:12
 * @Description: json转换实体类
 * @ClassName: DeviceData
 */
public class JsonRootBean {

    private String clientUuid;
    private String msg;
    private Date addTime;

    public void setClientUuid(String clientUuid) {
        this.clientUuid = clientUuid;
    }

    public String getClientUuid() {
        return clientUuid;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public Date getAddTime() {
        return addTime;
    }
}
