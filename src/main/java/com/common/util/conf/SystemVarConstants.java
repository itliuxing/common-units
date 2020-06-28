package com.common.util.conf;

/**
 * @Class SystemVarConstants
 * @Author 作者姓名:刘兴
 * @Version 1.0
 * @Date 创建时间：2018/8/17 13:14
 * @Copyright Copyright by 智多星
 * @Direction 类说明
 */
public class SystemVarConstants {

    public final static String imagepath = "photo/common/" ;

    public final static String FILE_PDF_PATH = "C:\\Program Files\\apache-tomcat-8.5.38\\webapps" ;

    public final static String PROJECT_PATH = "photo/common/" ;

    public static final String MEMBER_DEFAULT_PASSWORD = "123456" ;

    public final static Integer upLine = 1 ;
    public final static Integer downLine = 0 ;
    /* add by yn start */
    /* 系统日志模块类型 */
    public final static String MEMBER_LOG = "MEMBER";
    /* add end */


    // LocalCacheManager 本地缓存的有效期--默认值，均调用此处
    public static final Integer EXPIRE_TIME = 60 * 60 * 12 ;


    /***
     * 一天过期时间
     */
    public static final Integer EXPIRE_ONE_DAY = 24*60*60 ;


    public static final int DEFAULT_TYK_COLL_COUNT = 100 ;   //体验卡赠送收藏价条数
    public static final int DEFAULT_TYK_ASK_COUNT = 3 ;     //体验卡赠送询价条数
    public static final int DEFAULT_ASK_COUNT = 0 ;         //注册赠送询价条数
    public static final int DEFAULT_QURIY_DAY = 3 ;         //注册赠送市场价VIP天数
    public static final int DEFAULT_COLL_COUNT = 10 ;       //注册赠送收藏价条数
    public static final int DEFAULT_OTHER = 0 ;              //注册赠送其他条数


    public static final int DEFAULT_ASIS_ASK_COUNT = 0 ;    //助手赠送询价条数
    public static final int DEFAULT_ASIS_COLL_COUNT = 20 ;  //助手赠送收藏价条数
}
