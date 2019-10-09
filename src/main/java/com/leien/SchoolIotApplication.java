package com.leien;

import com.alibaba.fastjson.JSONObject;
import com.leien.utils.APIUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
@MapperScan("com.leien.dao")
public class SchoolIotApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolIotApplication.class, args);
        APIUtil api = new APIUtil();
        String token = api.getToken();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                String apiData = api.getData(token);
                System.out.println(">>>>>>"+apiData);
            }
        },300,3000);
    }
}
