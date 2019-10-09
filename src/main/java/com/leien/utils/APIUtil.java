package com.leien.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.leien.config.ApiConfig;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;

/**
 * @Compny:LeiEnChuanMei
 * @Auther: SSF
 * @Date: 2019/9/28 16:02
 * @Description:连接远程接口工具类
 */
@Component
public class APIUtil {

    private static ApiConfig api;

    @Autowired
    public void init(ApiConfig apiConfig) {
        this.api = apiConfig;
    }

    private Logger logger = LoggerFactory.getLogger(APIUtil.class);

    public String getData(String token){
        String str = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String url =  api.getDataUrl() + api.getDataPathName();//http://192.168.1.101:8881/iotApi/getReceiveMsgAll
        //封装请求参数
        List<NameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("project_key", api.getTokenKey()));
        if (StringUtils.isEmpty(token)){//如果Token为空就重新获取
            token = getToken();
        }
        try {
            str = EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));
            HttpGet get = new HttpGet(url+"?"+str);
            get.setHeader("access-token",token);
            //执行请求
            response = httpClient.execute(get);
            StatusLine status = response.getStatusLine();
            //获取状态码
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK){
                HttpEntity entity = response.getEntity();
                String resultData = EntityUtils.toString(entity, Consts.UTF_8);
                logger.info(resultData);
                return resultData;
            }else if (state == HttpStatus.SC_REQUEST_TIMEOUT){//408验证过期
                logger.error("验证过期");
                //token过期，重新获取
                token = getToken();
                get.setHeader("access-token",token);
            }else if(state == 401){//401没有权限
                logger.info("没有权限");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //消耗实体内容
            if(response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //关闭相应 丢弃http连接
            if(httpClient != null){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public String getToken(){
        String str = "";
        String jsonString = null;
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = api.getTokenUrl() + api.getTokenPathName();//http://192.168.1.101:8881/auth/token_sign;
        //封装请求参数
        List<NameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("token_key", api.getTokenKey()));

        try {
            //转换为键值对
            str = EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));
            System.out.println(str);
            //创建Get请求
            HttpGet httpGet = new HttpGet(url+"?"+str);
            //执行Get请求，
            response = httpClient.execute(httpGet);

            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                jsonString = EntityUtils.toString(responseEntity,Consts.UTF_8);
                return jsonString;
            }else{
                logger.error("请求返回:"+state+"("+url+")");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            //消耗实体内容
            if(response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //关闭相应 丢弃http连接
            if(httpClient != null){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
