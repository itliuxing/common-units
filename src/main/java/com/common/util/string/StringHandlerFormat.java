package com.common.util.string;

import com.common.util.number.NumberValidationUtil;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Class StringHandlerFormat
 * @Author 作者姓名:刘兴
 * @Version 1.0
 * @Date 创建时间：2018/11/1 11:00
 * @Copyright Copyright by 智多星
 * @Direction 类说明
 */
public class StringHandlerFormat {

    /***
     * 将input内的map-key字符替换成map-value
     * @param input
     * @param map
     * @return
     */
    public static String format(String input, Map<String, String> map) {

        // 遍历map,用value替换掉key
        for (Map.Entry<String, String> entry : map.entrySet()) {
            StringBuilder key = new  StringBuilder() ;
            key.append("${").append( entry.getKey() ).append("}") ;
            input = input.replace( key.toString() , entry.getValue() );
        }
        return input;
    }


    /***
     * 解析字符中的数据信息页面的页码信息
     * @param info
     * @return
     */
    public static String strToNumber( String info ) {
        if( StringUtils.isNotBlank(info) ) {
            String regEx="[^0-9]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(info);
            String pageStr = m.replaceAll("").trim() ;
            return pageStr ;
        }
        return null ;
    }

    /****
     * 名称内的多个空格在一起，则格式化成一个空格
     * @param str
     * @return
     */
    public static String matcherKongge( String str ){
        Pattern p = Pattern.compile("\\s+");
        Matcher m = p.matcher(str);
        return m.replaceAll(" ");
    }


    /***
     * 查找搜索关键字的信息
     * @param word
     * @return
     */
    public static int strToReplace( String word ){
        int index = word.indexOf(" ") ;
        if( index < 0 ) {
            index = word.indexOf("(");
            if( index < 0 ) {
                index = word.indexOf("（");
            }
        }
        return index ;
    }

    /*****
     * 全角符号转半角符号
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '\u3000') {
                c[i] = ' ';
            } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
                c[i] = (char) (c[i] - 65248);
            }
        }
        String returnString = new String(c);
        return returnString;
    }

    /***
     * 解析字符中的数据信息页面的页码信息
     * @param info
     * @return
     */
    public static boolean strIncludNumber( String info ) {
        boolean isInclud = false ;
        if( StringUtils.isNotBlank(info) ) {
            //1.1 【含有英文】true
            String regex1 = ".*[a-zA-z].*";
            isInclud = info.matches(regex1);
            if( isInclud ){
                return isInclud ;
            }
            //1.2 【含有数字】true
            String regex2 = ".*[0-9].*";
            isInclud = info.matches(regex2);
        }
        return isInclud ;
    }

    /***
     * 判断字符全部是数字
     * @param info
     * @return
     */
    public static boolean strAllToNumber( String info ) {
        boolean isAll = false ;
        if( StringUtils.isNotBlank(info) ) {
            //【全为数字】返回true
            isAll = info.matches("[0-9]+");
        }
        return isAll ;
    }




    public static void main(String[] args) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("supplierName", "湖南养天和健康药业有限公司");
        String input = "供应商：${supplierName}已提交注册信息，请及时上线审核处理！" ;
        System.out.println("结果:" + format( input, map) );
        System.out.println("结果:" + NumberValidationUtil.isRealNumber("25") );
        System.out.println("结果:" + NumberValidationUtil.isRealNumber("25.23") );
        System.out.println("结果:" + strAllToNumber("25") );
        System.out.println("结果:" + strAllToNumber("25.23") );
    }
}
