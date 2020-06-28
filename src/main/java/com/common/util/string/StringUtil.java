package com.common.util.string;

/**
 * @Class StringUtil
 * @Author 作者姓名:刘兴
 * @Version 1.0
 * @Date 创建时间：2020-06-17 14:49
 * @Copyright Copyright by wisestar company
 * @Direction 类说明
 */
public class StringUtil {

    /****
     * TODO 判断字符是否为空格、制表符、tab
     * @param str
     * @return
     */
    public static boolean isBlank(CharSequence str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                // 判断字符是否为空格、制表符、tab
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return true;
        }
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    /****
     * TODO 判断字符是否为null,长度为0
     * @param str
     * @return
     */
    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

}
