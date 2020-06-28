package com.common.util.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * @Class ThreadCommonUtil
 * @Author 作者姓名:刘兴
 * @Version 1.0
 * @Date 创建时间：2019-08-26 17:12
 * @Copyright Copyright by wisestar company
 * @Direction 类说明
 */
public class ThreadCommonUtil {

    private static Logger logger = LoggerFactory.getLogger( ThreadCommonUtil.class );
    private static Random random = new Random( ) ;

    /****
     *  公共随机睡眠
     * @param disturbVal    干扰值
     */
    public static void threadSleep( int disturbVal ){
        //第四步：随机睡眠，防止重复爬取
        try {
            int nextInt = random.nextInt( 5 * 1000 )  ;
            nextInt += disturbVal ;
            logger.info( "爬虫每次随机-------睡眠时间:{}" ,nextInt  );
            Thread.sleep( nextInt );
        } catch (InterruptedException e) { e.printStackTrace(); }
    }

    /****
     *  公共随机睡眠
     * @param disturbVal    指定随机睡眠值
     */
    public static void threadThresholdSleep( int disturbVal ){
        //第四步：随机睡眠，防止重复爬取
        try {
            int nextInt = random.nextInt( disturbVal )  ;
            logger.info( "爬虫每次随机-------睡眠时间:{}" ,nextInt  );
            Thread.sleep( nextInt );
        } catch (InterruptedException e) { e.printStackTrace(); }
    }


    /****
     *  公共随机睡眠
     * @param disturbVal    干扰值
     */
    public static void threadMinSleep( int disturbVal ){
        //第四步：随机睡眠，防止重复爬取
        try {
            int nextInt = random.nextInt( 60 * 1000 )  ;
            nextInt += disturbVal ;
            logger.info( "爬虫每次随机-------睡眠时间:{}" ,nextInt  );
            Thread.sleep( nextInt );
        } catch (InterruptedException e) { e.printStackTrace(); }
    }

    /****
     *  多少天以后，1000天，再一个干扰值
     * @param disturbVal    干扰值
     */
    public static int afterDay( int disturbVal ){
        int nextInt = random.nextInt( 1000 )  ;
        nextInt += disturbVal ;
        logger.info( "每次随机-------创建时间:{}" ,nextInt  );
        return nextInt ;
    }

    /****
     *  多少天以前，1000天，再一个干扰值
     * @param disturbVal    干扰值
     */
    public static int beforeDay( int disturbVal ){
        int nextInt = random.nextInt( 1000 )  ;
        nextInt -= disturbVal ;
        logger.info( "每次随机-------创建时间:{}" ,nextInt  );
        return nextInt ;
    }
}
