package com.leien.utils;

import com.leien.config.ApiConfig;
import com.leien.entity.School;
import com.leien.service.SchoolService;
import org.apache.http.*;
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
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Compny:LeiEnChuanMei
 * @Author: SSF
 * @Date: 2019/9/28 16:02
 * @Description:连接远程接口工具类
 */
@Component
public class APIUtil {

    private static ApiConfig api;

    @Autowired
    SchoolService schoolService;

    /**
     * 注入访问远程设备配置类
     * @param apiConfig
     */
    @Autowired
    public void init(ApiConfig apiConfig) {
        APIUtil.api = apiConfig;
    }

    private Logger logger = LoggerFactory.getLogger(APIUtil.class);


    /**
     * 获取学校项目标识符
     * @return
     */
    public String getSchoolKey(){
        StringBuilder sb = new StringBuilder();
        List<School> schoolProjectKey = schoolService.getSchoolProjectKey();
        for(School school : schoolProjectKey){
            sb.append(school.getSchoolProjectKey()).append(",");
        }
        sb.substring(0, sb.length()-1);
        return sb.toString();
    }

    /**
     * 获取设备列表
     * @param token
     * @return
     */
    public String getDeviceInformation(String token){
        String schoolKey = getSchoolKey();
        String str = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String url =  api.getUrl() + api.getDeviceInformationPathName();
        //封装请求参数
        List<NameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("project_key", schoolKey));
        //如果Token为空就重新获取
        if (StringUtils.isEmpty(token)){
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

                return resultData;
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

    /**
     * 访问远程设备接口获取设备返回数据
     * @param token
     * @return
     */
    public String getData(String token){
        String str = "";
        String schoolKey = getSchoolKey();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String url =  api.getUrl() + api.getDataPathName();
        //封装请求参数
        List<NameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("project_key", schoolKey));
        //如果Token为空就重新获取
        if (StringUtils.isEmpty(token)){
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
                return resultData;
                //408验证过期
            }else if (state == HttpStatus.SC_REQUEST_TIMEOUT){
                logger.error("验证过期");
                //token过期，重新获取
                token = getToken();
                get.setHeader("access-token",token);
                //401没有权限
            }else if(state == 401){
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

    /**
     * 获取token
     * @return
     */
    public String getToken(){
        String str = "";
        String jsonString = null;
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = api.getUrl() + api.getTokenPathName();
        //封装请求参数
        List<NameValuePair> params = new ArrayList();
        params.add(new BasicNameValuePair("token_key", api.getTokenKey()));

        try {
            //转换为键值对
            str = EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));
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
