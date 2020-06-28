package com.common.util.string.word;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Class WordMainTest
 * @Author 作者姓名:刘兴
 * @Version 1.0
 * @Date 创建时间：2020-05-22 14:52
 * @Copyright Copyright by wisestar company
 * @Direction 类说明
 */
public class WordMainTest {

    private static Logger logger = LoggerFactory.getLogger( WordMainTest.class ) ;

    public static void main(String[] args) throws Exception {
        //输入的字符串
        String str = "你好，帮我查看下今天的广材网、慧讯网、湘建网、造价通 的价格状况信息";
        long l1 = System.currentTimeMillis() ;
        String response = SensitivewordFilter.replaceSensitiveWord( str,1,"*");
        long l2 = System.currentTimeMillis() ;
        response = SensitivewordFilter.replaceSensitiveWord( str,1,"*");
        long l3 = System.currentTimeMillis() ;
        logger.info("第一次需要加载配置文件耗时：{} ... {}" ,  (l2-l1) , response );
        logger.info("第二次纯内存计算耗时：{} ... {} " ,  (l3-l2) , response );

    }

}
