package com.leien.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Compny:LeiEnChuanMei
 * @Auther: SSF
 * @Date: 2019/9/29 12:02
 * @Description:调用设备远程接口配置类
 */
@Configuration
@ConfigurationProperties(prefix = "api")
@PropertySource("classpath:config/api.properties")
@Component
public class ApiConfig {
    private String tokenUrl;
    private String tokenPathName;
    private String token_key;
    private String dataUrl;
    private String dataPathName;

    public String getTokenUrl() {
        return tokenUrl;
    }

    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    public String getTokenPathName() {
        return tokenPathName;
    }

    public void setTokenPathName(String tokenPathName) {
        this.tokenPathName = tokenPathName;
    }

    public String getToken_key() {
        return token_key;
    }

    public void setToken_key(String token_key) {
        this.token_key = token_key;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public String getDataPathName() {
        return dataPathName;
    }

    public void setDataPathName(String dataPathName) {
        this.dataPathName = dataPathName;
    }

    @Override
    public String toString() {
        return "ApiConfig{" +
                "tokenUrl='" + tokenUrl + '\'' +
                ", tokenPathName='" + tokenPathName + '\'' +
                ", token_key='" + token_key + '\'' +
                ", dataUrl='" + dataUrl + '\'' +
                ", dataPathName='" + dataPathName + '\'' +
                '}';
    }
}
