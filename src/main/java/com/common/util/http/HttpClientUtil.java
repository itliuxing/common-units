package com.common.util.http;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Class HttpClientResponse
 * @Author 作者姓名:刘兴
 * @Version 1.0
 * @Date 创建时间：2019-04-23 15:11
 * @Copyright Copyright by wisestar company
 * @Direction 类说明
 */
public class HttpClientUtil {


    private final static int http_connect_timeout = 1000 * 5 ;     //连接超时时间
    private final static int http_socket_timeout = 1000 * 5 ;     //数据超时时间
        
    /**  
     * 发送http get请求  
     */    
    public static HttpClientResponse httpGet(String url, Map<String,String> headers, String encode){
        HttpClientResponse response = new HttpClientResponse();
        if(encode == null){
            encode = "utf-8";
        }
        String content = null;
        //since 4.3 不再使用 DefaultHttpClient
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        //设置header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpGet.setHeader(entry.getKey(),entry.getValue());
            }
        }
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = closeableHttpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
            response.setBody(content);
            response.setHeaders(httpResponse.getAllHeaders());
            response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
            response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {  //关闭连接、释放资源
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    /**
     * 发送 http post 请求，参数以form表单键值对的形式提交。
     */
    public static HttpClientResponse httpPostForm(String url,Map<String,String> params, Map<String,String> headers,String encode){
        HttpClientResponse response = new HttpClientResponse();
        if(encode == null){
            encode = "utf-8";
        }
        //HttpClients.createDefault()等价于 HttpClientBuilder.create().build();
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPost httpost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout( http_socket_timeout ).setConnectTimeout( http_connect_timeout ).build();//设置请求和传输超时时间
        httpost.setConfig(requestConfig);
        //设置header
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpost.setHeader(entry.getKey(),entry.getValue());
            }
        }
        //组织请求参数
        List<NameValuePair> paramList = new ArrayList <NameValuePair>();
        if(params != null && params.size() > 0){
            Set<String> keySet = params.keySet();
            for(String key : keySet) {
                paramList.add(new BasicNameValuePair(key, params.get(key)));
            }
        }
        try {
            httpost.setEntity(new UrlEncodedFormEntity(paramList, encode));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String content = null;
        CloseableHttpResponse  httpResponse = null;
        try {
            httpResponse = closeableHttpClient.execute(httpost);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
            response.setBody(content);
            response.setHeaders(httpResponse.getAllHeaders());
            response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
            response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {  //关闭连接、释放资源
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 发送 http post 请求，参数以原生字符串进行提交
     * @param url
     * @param encode
     * @return
     */
    public static HttpClientResponse httpPostRaw(String url,String stringJson,Map<String,String> headers, String encode){
        HttpClientResponse response = new HttpClientResponse();
        if(encode == null){
            encode = "utf-8";
        }
        //HttpClients.createDefault()等价于 HttpClientBuilder.create().build();
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPost httpost = new HttpPost(url);
        //设置超时信息
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();//设置请求和传输超时时间
        httpost.setConfig(requestConfig);

        //设置header
        httpost.setHeader("Content-type", "application/json");
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpost.setHeader(entry.getKey(),entry.getValue());
            }
        }
        //组织请求参数
        StringEntity stringEntity = new StringEntity(stringJson, encode);
        httpost.setEntity(stringEntity);
        String content = null;
        CloseableHttpResponse  httpResponse = null;
        try {
            //响应信息
            httpResponse = closeableHttpClient.execute(httpost);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
            response.setBody(content);
            response.setHeaders(httpResponse.getAllHeaders());
            response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
            response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {  //关闭连接、释放资源
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 发送 http put 请求，参数以原生字符串进行提交
     * @param url
     * @param encode
     * @return
     */
    public static HttpClientResponse httpPutRaw(String url,String stringJson,Map<String,String> headers, String encode){
        HttpClientResponse response = new HttpClientResponse();
        if(encode == null){
            encode = "utf-8";
        }
        //HttpClients.createDefault()等价于 HttpClientBuilder.create().build();
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPut httpput = new HttpPut(url);

        //设置header
        httpput.setHeader("Content-type", "application/json");
        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpput.setHeader(entry.getKey(),entry.getValue());
            }
        }
        //组织请求参数
        StringEntity stringEntity = new StringEntity(stringJson, encode);
        httpput.setEntity(stringEntity);
        String content = null;
        CloseableHttpResponse  httpResponse = null;
        try {
            //响应信息
            httpResponse = closeableHttpClient.execute(httpput);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
            response.setBody(content);
            response.setHeaders(httpResponse.getAllHeaders());
            response.setReasonPhrase(httpResponse.getStatusLine().getReasonPhrase());
            response.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            closeableHttpClient.close();  //关闭连接、释放资源
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /****
     * post 请求
     * @param url
     * @param jsonStr
     * @return
     */
    public static String httpClientPost(String url, String jsonStr) {
        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter("http.protocol.content-charset",
                "UTF-8");
        httpclient.getParams().setParameter("Content-Type", "application/json");
        HttpPost httppost = new HttpPost(url);

        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        String responseBody = "";
        try {
            // 设置菜单参数
            StringEntity reqEntity = new StringEntity(jsonStr, "utf-8");
            // 设置请求的数据
            httppost.setEntity(reqEntity);
            responseBody = httpclient.execute(httppost, responseHandler);
            return responseBody;
        } catch (Exception e) {

        }
        return null;
    }
}