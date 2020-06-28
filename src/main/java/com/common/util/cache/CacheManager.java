package com.common.util.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Class CacheManager
 * @Author 作者姓名:刘兴
 * @Version 1.0
 * @Date 创建时间：2019/1/9 11:04
 * @Copyright Copyright by 智多星
 * @Direction 类说明       来自互联网
 */
public class CacheManager {

    public static final Logger logger = LoggerFactory.getLogger( CacheManager.class ) ;

    private static Map<String,CacheData> CACHE_DATA = new ConcurrentHashMap<>();

    /***
     * 获取数据，如果没有数据，提供再写入数据
     * @param key
     * @param load
     * @param expire
     * @param <T>
     * @return
     */
    public static <T> T getData(String key,Load<T> load,int expire){
        T data = getData(key);
        if(data == null && load != null){
            data = load.load();
            if(data != null){
                setData(key,data,expire);
            }
        }
        return data;
    }

    /***
     * 单纯的获取数据
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T getData(String key){
        CacheData<T> data = CACHE_DATA.get(key);
        if(data != null && (data.getExpire() <= 0 || data.getSaveTime() >= new Date().getTime())){
            return data.getData();
        }
        return null;
    }

    /***
     * 写入数据
     * @param key
     * @param data
     * @param expire
     * @param <T>
     */
    public static <T> void setData(String key,T data,int expire){
        CACHE_DATA.put(key,new CacheData(data,expire));
    }

    /***
     * 清除此key的数据
     * @param key
     */
    public static void clear(String key){
        CACHE_DATA.remove(key);
    }

    /***
     * 清除所有的数据
     */
    public static void clearAll(){
        CACHE_DATA.clear();
    }

    /***
     * 实现用
     * @param <T>
     */
    public interface Load<T>{
        T load();
    }

    /****
     * 实际记录失效的对象
     * @param <T>
     */
    private static class CacheData<T>{
        CacheData(T t,int expire){
            this.data = t;
            this.expire = expire <= 0 ? 0 : expire * 1000;
            this.saveTime = new Date().getTime() + this.expire;
        }
        private T data;
        private long saveTime; // 存活时间
        private long expire;   // 过期时间 小于等于0标识永久存活
        public T getData() {
            return data;
        }
        public long getExpire() {
            return expire;
        }
        public long getSaveTime() {
            return saveTime;
        }
    }

    public static void main(String [] args)throws Exception{
        //测试------------->>>>>>>>>>>>------------>>>>>>>>>>>>
        String key = "CacheManagerHashKey" ;

        CacheManager.setData( key , true , 5 );

        Boolean isTrue = CacheManager.getData( key ) ;
        logger.info( "手机号是否能够发送短信:"+isTrue );
        Thread.sleep(6 * 1000);
        isTrue = CacheManager.getData( key ) ;
        logger.info( "手机号是否能够发送短信:"+isTrue );

        String value = CacheManager.getData(key, new CacheManager.Load<String>() {
            public String load(){
                return "testValue";
            }
        },2);

        logger.info("value:"+value);

        Thread.sleep(3 * 1000);

        String value2 = CacheManager.getData(key, new CacheManager.Load<String>() {
            @Override
            public String load() {
                return "什么";
            }
        },3);
        System.out.println("value2:" + value2);
        System.out.println("value3:" + CacheManager.getData(key));

    }

}
