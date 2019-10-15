package com.leien;

import com.leien.utils.APIUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SchoolIotApplicationTests {

    @Test
    public void contextLoads() {
        APIUtil api = new APIUtil();
        String token = api.getToken();
        System.out.println("--------"+token);
        String apiData = api.getData(token);
        System.out.println(">>>>>>"+apiData);
    }

}
