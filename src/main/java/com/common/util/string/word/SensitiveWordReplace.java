package com.common.util.string.word;

import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * @Class SensitiveWordReplace
 * @Author 作者姓名:刘兴
 * @Version 1.0
 * @Date 创建时间：2020-06-06 09:38
 * @Copyright Copyright by wisestar company
 * @Direction 类说明
 */
public class SensitiveWordReplace {

    public static Map<String,String> sensitiveWordReplaceMap = Maps.newHashMap() ;

    static{
        sensitiveWordReplaceMap.put( "立方米" , "m3") ;
        sensitiveWordReplaceMap.put( "平方米" , "m2") ;
        sensitiveWordReplaceMap.put( "千米" , "km") ;
        sensitiveWordReplaceMap.put( "㎡" , "m2") ;

        sensitiveWordReplaceMap.put( "吨" , "t") ;
        sensitiveWordReplaceMap.put( "千克" , "kg") ;
        sensitiveWordReplaceMap.put( "公斤" , "kg") ;
        sensitiveWordReplaceMap.put( "克" , "g") ;
        sensitiveWordReplaceMap.put( "Kg" , "kg") ;
        sensitiveWordReplaceMap.put( "KG" , "kg") ;

        sensitiveWordReplaceMap.put( "kW·h" , "kW.h") ;
    }

    /****
     * 关键字替换
     * @param word
     * @return
     */
    public static String wordReplace( String word ){
        if(StringUtils.isNotBlank( word )){
            String tempWord = sensitiveWordReplaceMap.get( word ) ;
            if( StringUtils.isNotBlank( tempWord ) ){
                word = tempWord ;
            }
        }
        return word ;
    }

}
