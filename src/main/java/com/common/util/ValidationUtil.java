package com.common.util;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * @Class ValidationUtil
 * @Author 作者姓名:刘兴
 * @Version 1.0
 * @Date 创建时间：2018/12/26 10:07
 * @Copyright Copyright by 智多星
 * @Direction 类说明
 */
public class ValidationUtil {

    /***
     * 验证手机号格式是否正确
     * @param mobile
     * @param isRequired
     * @return
     */
    public static boolean isMobile(String mobile, boolean isRequired) {
        if (StringUtils.isBlank(mobile) && isRequired) {
            return false;
        }
        String regexp = "(1(3|5|7|8)\\d|146|148|166|198|199)((\\*{4}|\\d{4}))\\d{4}$";
        Pattern pattern = Pattern.compile(regexp);
        return pattern.matcher(mobile).matches();
    }

    /**
     * 验证价格格式是否正确
     * @param price
     * @param isRequired
     * @return
     */
    public static boolean checkPrice(String price,boolean isRequired){
        if(StringUtils.isBlank(price) && isRequired){
            return false;
        }
        String regex = "\\d\\.\\d*|[1-9]\\d*|\\d*\\.\\d*|\\d";
        Pattern pattern = Pattern.compile(regex); //将给定的正则表达式编译到模式中。
        return pattern.matcher(price).matches();
    }

    /**
     * 验证总价相等的问题
     * @param price
     * @param quantity
     * @param totalPrice
     * @param isRequired
     * @return
     */
    public static boolean checkTotalPrice(String price,String quantity,String totalPrice,boolean isRequired){
        if(StringUtils.isBlank(price) && StringUtils.isBlank(quantity) && StringUtils.isBlank(totalPrice) && isRequired){
            return false;
        }
        BigDecimal newTotalPrice = new BigDecimal(price).multiply(new BigDecimal(quantity));
        newTotalPrice = newTotalPrice.setScale(2,BigDecimal.ROUND_HALF_UP);
        BigDecimal oldTotalPrice = new BigDecimal(totalPrice).setScale(2,BigDecimal.ROUND_HALF_UP);
        if(newTotalPrice.compareTo(oldTotalPrice)==0){
            return true;
        }
        return false;
    }

}
