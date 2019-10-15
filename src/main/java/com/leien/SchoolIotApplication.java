package com.leien;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Compny:LeiEnChuanMei
 * @Author: SSF
 * @Date: 2019/10/10 17:01
 * @Description:
 * @ClassName: SchoolIotApplication
 */

@SpringBootApplication
@MapperScan("com.leien.dao")
public class SchoolIotApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolIotApplication.class, args);
    }
}
