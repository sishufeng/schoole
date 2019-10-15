package com.leien.applicationrunner;

import com.leien.utils.APIUtil;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Compny:LeiEnChuanMei
 * @Author: SSF
 * @Date: 2019/10/9 11:57
 * @Description: SpringBoot项目启动时自动执行指定方法
 */
@Component
@Order(value = 1)
public class AppRunner implements ApplicationRunner {

    int count = 0;
    public List pageList = new ArrayList();
    public List dataList = new ArrayList();
    @Autowired
    APIUtil apiUtil;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String token = apiUtil.getToken();

        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                //do something
                if (!StringUtils.isEmpty(token)){
                    String returnData = apiUtil.getData(token);
                    pageList.add(returnData);
                    count++;
                    if (count == 20){//1分钟往数据库保存一次
                        dataList.add(returnData);
                        System.out.println(">>>>>>>>>>>>++++++++>>>>>>"+returnData);
                        count = 0;
                    }
                }
            }
        },3000,3000, TimeUnit.MILLISECONDS);

    }
}
