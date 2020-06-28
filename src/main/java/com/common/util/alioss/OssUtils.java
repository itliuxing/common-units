package com.common.util.alioss;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.common.util.conf.PropertiesUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * This sample demonstrates how to upload/download an object to/from 
 * Aliyun OSS using the OSS SDK for Java.
 */
public class OssUtils{
	
    private static String endpoint = PropertiesUtils.getInstance().getKey("aliyunoss_endpoint");
    private static String accessKeyId = PropertiesUtils.getInstance().getKey("aliyunoss_accesskeyid");
    private static String accessKeySecret = PropertiesUtils.getInstance().getKey("aliyunoss_accesskeysecret");
    private static String bucketName = PropertiesUtils.getInstance().getKey("aliyunoss_bucketname");
    //图片在阿里云存放的前置路径
    public static  String ossUrl = PropertiesUtils.getInstance().getKey("aliyunoss_url");
    /* 这个是水印内容 */
    public static String waterMarkContent  = PropertiesUtils.getInstance().getKey(OssProperties.WATER_MARK_CONTENT);
    /* 这个是加急审核推送规则 */
    public static String customerPushTime = PropertiesUtils.getInstance().getKey(OssProperties.PRODUCT_RELEASE_PUSH);
    
    
    static Logger logger = LoggerFactory.getLogger(OssUtils.class);

    /**
     * 获取加密url
     * @param bucketName
     * @param key
     * @param expiration
     * @return
     */
    public static URL generatePresignedUrl(String bucketName, String key, Date expiration){
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        return client.generatePresignedUrl(bucketName,key,expiration);
    }
    
    /**
     * 
     * @Title: getKeyPath   
     * @Description: 获取路径
     * @param: @param key
     * @param: @return      
     * @return: String      
     * @throws
     */
    public static String getKeyPath(String key)  {
        return key.replace("\\", "/");
    }
    
    /**
     * 
     * @Title: upload   
     * @Description: 上传
     * @param: @param key
     * @param: @param is
     * @param: @throws IOException      
     * @return: void      
     * @throws
     */
    public static void upload(String key,InputStream is) throws IOException {
        /*
         * Constructs a client instance with your account for accessing OSS
         */
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);



        try {
            
            /**
             * Note that there are two ways of uploading an object to your bucket, the one 
             * by specifying an input stream as content source, the other by specifying a file.
             */            
            client.putObject(bucketName, key,  is);
            // 设置Object权限
            client.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);
            
        } catch (OSSException oe) {
            logger.info("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            logger.info("Error Message: " + oe.getErrorCode());
            logger.info("Error Code:       " + oe.getErrorCode());
            logger.info("Request ID:      " + oe.getRequestId());
            logger.info("Host ID:           " + oe.getHostId());
        } catch (ClientException ce) {
            logger.info("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            logger.info("Error Message: " + ce.getMessage());
        } finally {
            /*
             * Do not forget to shut down the client finally to release all allocated resources.
             */
            client.shutdown();
        }
    }
    
    /**
     * 
     * @Title: upload   
     * @Description: 上传
     * @param: @param key
     * @param: @param is
     * @param: @throws IOException      
     * @return: void      
     * @throws
     */
    public static void upload(String key,File file) throws IOException {
        /*
         * Constructs a client instance with your account for accessing OSS
         */

        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            
            /**
             * Note that there are two ways of uploading an object to your bucket, the one 
             * by specifying an input stream as content source, the other by specifying a file.
             */            
            client.putObject(bucketName, key,  file);
            // 设置Object权限
            client.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);
            
        } catch (OSSException oe) {
            logger.info("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            logger.info("Error Message: " + oe.getErrorCode());
            logger.info("Error Code:       " + oe.getErrorCode());
            logger.info("Request ID:      " + oe.getRequestId());
            logger.info("Host ID:           " + oe.getHostId());
        } catch (ClientException ce) {
            logger.info("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            logger.info("Error Message: " + ce.getMessage());
        } finally {
            /*
             * Do not forget to shut down the client finally to release all allocated resources.
             */
            client.shutdown();
        }
    }
   
    /**
     * 
     * @Title: download   
     * @Description: 下载
     * @param: @param key
     * @param: @return
     * @param: @throws IOException      
     * @return: InputStream      
     * @throws
     */
    public static InputStream download(String key) throws IOException {
        /*
         * Constructs a client instance with your account for accessing OSS
         */
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        
        try {
            
          
            /*
             * Download an object from your bucket
             */
            logger.info("Downloading an object");
            OSSObject object = client.getObject(new GetObjectRequest(bucketName, key));
            logger.info("Content-Type: "  + object.getObjectMetadata().getContentType());
            return object.getObjectContent();
            
        } catch (OSSException oe) {
            logger.info("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            logger.info("Error Message: " + oe.getErrorCode());
            logger.info("Error Code:       " + oe.getErrorCode());
            logger.info("Request ID:      " + oe.getRequestId());
            logger.info("Host ID:           " + oe.getHostId());
        } catch (ClientException ce) {
            logger.info("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            logger.info("Error Message: " + ce.getMessage());
        } finally {
            /*
             * Do not forget to shut down the client finally to release all allocated resources.
             */
            client.shutdown();
        }
        
        return null;
    }
    
    /**
     * 
     * @Title: checkObjectExist   
     * @Description: 检查是否存在
     * @param: @param key
     * @param: @return
     * @param: @throws IOException      
     * @return: boolean      
     * @throws
     */
    public static boolean checkObjectExist(String key) throws IOException {
       
    	 /*
         * Constructs a client instance with your account for accessing OSS
         */
        OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        
        try {
            return client.doesObjectExist(bucketName, key);
            
        } catch (OSSException oe) {
            logger.info("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            logger.info("Error Message: " + oe.getErrorCode());
            logger.info("Error Code:       " + oe.getErrorCode());
            logger.info("Request ID:      " + oe.getRequestId());
            logger.info("Host ID:           " + oe.getHostId());
            return false;
        } catch (ClientException ce) {
            logger.info("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            logger.info("Error Message: " + ce.getMessage());
            return false;
        } finally {
            /*
             * Do not forget to shut down the client finally to release all allocated resources.
             */
            client.shutdown();
        }
        
    }
    
    
    /**
     * 
     * @Title: deleteObject   
     * @Description: 删除
     * @param: @param key
     * @param: @throws IOException      
     * @return: void      
     * @throws
     */
    public static void deleteObject(String key) throws IOException {
        
   	 /*
        * Constructs a client instance with your account for accessing OSS
        */
       OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
       
       try {
    	   logger.warn("删除数据："+key);
           client.deleteObject(bucketName, key);
           
       } catch (OSSException oe) {
           logger.info("Caught an OSSException, which means your request made it to OSS, "
                   + "but was rejected with an error response for some reason.");
           logger.info("Error Message: " + oe.getErrorCode());
           logger.info("Error Code:       " + oe.getErrorCode());
           logger.info("Request ID:      " + oe.getRequestId());
           logger.info("Host ID:           " + oe.getHostId());
       } catch (ClientException ce) {
           logger.info("Caught an ClientException, which means the client encountered "
                   + "a serious internal problem while trying to communicate with OSS, "
                   + "such as not being able to access the network.");
           logger.info("Error Message: " + ce.getMessage());
       } finally {
           /*
            * Do not forget to shut down the client finally to release all allocated resources.
            */
           client.shutdown();
       }
       
   }





}
