package com.leien.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Compny:LeiEnChuanMei
 * @Auther: SSF
 * @Date: 2019/9/29 12:02
 * @Description:调用设备远程接口配置类，从配置文件读取数据
 */
@Configuration
@ConfigurationProperties(prefix = "api")
@EnableConfigurationProperties
@PropertySource("application.properties")
@Component
@Data
public class ApiConfig {
    private String tokenUrl;
    private String tokenPathName;
    private String tokenKey;
    private String dataUrl;
    private String dataPathName;
}
