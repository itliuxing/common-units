package com.common.util.string.word;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Class SensitivewordFilter
 * @Author 作者姓名:刘兴
 * @Version 1.0
 * @Date 创建时间：2020-05-22 14:51
 * @Copyright Copyright by wisestar company
 * @Direction 类说明  敏感词过滤
 */
public class SensitivewordFilter {

    @SuppressWarnings("rawtypes")
    private static Map sensitiveWordMap = null;
    public static int minMatchTYpe = 1; //最小匹配规则
    public static int maxMatchType = 2; //最大匹配规则

    public static List<String> datas ;


    static{
        if( datas == null ) {
           try {
               //从文件中读取词库中的内容，将内容添加到list集合中
               datas = SensitiveWordInit.readSensitiveWordFile();
               //初始化词库,读取敏感词库，将敏感词放入HashSet中，构建一个DFA算法模型：
               SensitiveWordInit.init(datas);
           }catch (Exception e){}
        }
    }

    private static void initSensitiveWord(List<String> datas) {
        sensitiveWordMap = SensitiveWordInit.init(datas);
    }

    /**
     * 替换敏感字字符
     * @author chenming
     * @date 2014年4月20日 下午5:12:07
     * @param txt
     * @param matchType
     * @param replaceChar 替换字符，默认*
     * @version 1.0
     */
    public static String replaceSensitiveWord( String txt,int matchType,String replaceChar){
        if (sensitiveWordMap == null) {
            initSensitiveWord(datas);
        }
        String resultTxt = txt;
        List<String> set = SensitiveWordInit.getSensitiveWord( txt, matchType ); //获取所有的敏感词
        System.out.println(set);
        Iterator<String> iterator = set.iterator();
        String word = null;
        String replaceString = null;
        while (iterator.hasNext()) {
            word = iterator.next();
            replaceString = getReplaceChars(replaceChar, word.length());
            resultTxt = resultTxt.replaceAll(word, replaceString);
        }

        return resultTxt;
    }

    /**
     * 判断传入信息-是否存在敏感字-字符
     * @author chenming
     * @date 2014年4月20日 下午5:12:07
     * @param txt
     * @param matchType
     * @version 1.0
     */
    public static boolean isExistSensitiveWord( String txt,int matchType){
        if( StringUtils.isBlank(txt)  ){
            return false ;
        }
        if (sensitiveWordMap == null) {
            initSensitiveWord(datas);
        }
        //获取传入文档内的-所有的敏感词列表
        List<String> set = SensitiveWordInit.getSensitiveWord( txt, matchType );
        if( set != null && set.size() > 0 ){
            return true ;
        }
        return false;
    }

    /**
     * 获取与词库中相匹配的词语
     * @author chenming
     * @date 2014年4月20日 下午5:12:07
     * @param datas
     * @param txt
     * @param matchType
     * @version 1.0
     */
    public static List<String> getSensitiveWord(List<String> datas, String txt,int matchType){
        if (sensitiveWordMap == null) {
            initSensitiveWord(datas);
        }
        List<String> list = SensitiveWordInit.getSensitiveWord(txt, matchType); //获取所有的敏感词
        return list;
    }
    /**
     * 获取替换字符串
     * @author chenming
     * @date 2014年4月20日 下午5:21:19
     * @param replaceChar
     * @param length
     * @return
     * @version 1.0
     */
    private static String getReplaceChars(String replaceChar,int length){
        String resultReplace = replaceChar;
        if (length > 6) {
            length = 6;
        }
        for(int i = 1 ; i < length ; i++){
            resultReplace += replaceChar;
        }
        return resultReplace;
    }

    /**
     * 添加敏感词
     * @param str
     */
    private static boolean addSensitiveWord(String str) {
        if (sensitiveWordMap == null) {
            return false;
        }
        Map nowMap = null;
        Map<String, String> newWorMap = null;
        nowMap = sensitiveWordMap;
        for(int i = 0 ; i < str.length() ; i++){
            char keyChar = str.charAt(i); //转换成char型
            Object wordMap = nowMap.get(keyChar); //获取
            if(wordMap != null){ //如果存在该key，直接赋值
                nowMap = (Map) wordMap;
            }else{ //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                newWorMap = new HashMap<String,String>();
                newWorMap.put("isEnd", "0"); //不是最后一个
                nowMap.put(keyChar, newWorMap);
                nowMap = newWorMap;
            }
            if(i == str.length() - 1){
                nowMap.put("isEnd", "1"); //最后一个
            }
        }
        return  true;
    }

    public static void main(String[] args) {
        SensitivewordFilter.isExistSensitiveWord( "2020年长沙市01月信息价" ,1 ) ;
    }

}


