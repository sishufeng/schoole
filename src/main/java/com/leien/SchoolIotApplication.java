package com.leien;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.leien.dao")
public class SchoolIotApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolIotApplication.class, args);
    }

}
