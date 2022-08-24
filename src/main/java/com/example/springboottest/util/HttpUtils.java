package com.example.springboottest.util;


import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONReader;
import com.example.springboottest.common.FailResultException;
import com.example.springboottest.common.commonstatic.CommonCode;
import com.example.springboottest.common.commonstatic.CommonMessage;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

@Slf4j
public class HttpUtils {

    public static <T> T postSend(String strUrl,String param,Class<T> clazz) throws FailResultException {


        URL url = null;
        HttpURLConnection connection = null;

        try {
            url = new URL(strUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.addRequestProperty("Content-Type","application/json;charset=utf-8");
            connection.setRequestProperty("Accept-Charset", "utf-8");
            connection.setRequestProperty("contentType", "utf-8");
            connection.connect();

            //POST方法时使用
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.write(param.getBytes("utf-8"));
            out.flush();
            out.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            log.info(buffer.toString());
            T t = JSONObject.parseObject(buffer.toString(),clazz);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            throw new FailResultException(CommonCode.CODE_50001, e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }


    }


    /**
     * 发送HTTP消息
     * 用get请求向连接地址发送消息
     * @Author 孟庆梁
     * @date 2022/8/23
     * @param strUrl 链接地址
     * @param param 请求信息
     * @param clazz 返回参数类型
     * @return T 返回java对象
     */
    public static <T> T getSend(String strUrl,String param,Class<T> clazz) throws FailResultException {
        URL url = null;
        HttpURLConnection connection = null;

        try {
            if(param != null){
                strUrl += "?"+ param;
            }
            url = new URL(strUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            log.info(buffer.toString());
            reader.close();
            T t = JSONObject.parseObject(buffer.toString(),clazz);
            return t;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}