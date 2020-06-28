package com.common.util.http;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @Class 	URLVerify.java
 * @Author 	作者姓名:刘兴 
 * @Version	1.0
 * @Date	创建时间：2017-8-18 下午4:57:37
 * @Copyright Copyright by 智多星
 * @Direction 类说明
 */

public class HttpURLVerify {

	/****
	 * 验证链接的有效性
	 * @param webUrl
	 * @return
	 */
	public static boolean testUrl(String webUrl) {  
        try {  
            // 设置此类是否应该自动执行 HTTP重定向（响应代码为 3xx 的请求）。  
            HttpURLConnection.setFollowRedirects(false);  
            // 到URL所引用的远程对象的连接  
            HttpURLConnection conn = (HttpURLConnection) new URL(webUrl).openConnection();  
            // 设置URL请求的方法，GET POST HEAD OPTIONS PUT DELETE TRACE  
            // 以上方法之一是合法的，具体取决于协议的限制。  
            conn.setRequestMethod("HEAD");  
            // 从HTTP响应消息获取状态码  
            return (conn.getResponseCode() == HttpURLConnection.HTTP_OK);  
        } catch (Exception e) {  
            return false;  
        }  
    }  
	
	/**
     * 解析出url参数中的键值对
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     *
     * @param URL url地址
     * @return url请求参数部分
     */
    public static Map<String, String> URLRequest(String URL) {
        Map<String, String> mapRequest = new HashMap<String, String>();
 
        String[] arrSplit = null;
 
        String strUrlParam = TruncateUrlPage(URL);
        if (strUrlParam == null) {
            return mapRequest;
        }
        //每个键值为一组 www.2cto.com
        arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]");
 
            //解析出键值
            if (arrSplitEqual.length > 1) {
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
 
            } else {
                if (arrSplitEqual[0] != "") {
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }
	
    /**
     * 去掉url中的路径，留下请求参数部分
     *
     * @param strURL url地址
     * @return url请求参数部分
     */
    private static String TruncateUrlPage(String strURL) {
        String strAllParam = null;
        String[] arrSplit = null;
 
        strURL = strURL.trim();
 
        arrSplit = strURL.split("[?]");
        if (strURL.length() > 1) {
            if (arrSplit.length > 1) {
                if (arrSplit[1] != null) {
                    strAllParam = arrSplit[1];
                }
            }
        }
 
        return strAllParam;
    }
}
