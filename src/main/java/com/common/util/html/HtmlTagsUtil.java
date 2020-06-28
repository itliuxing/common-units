package com.common.util.html;

import com.common.util.string.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * Copyright@ Apache Open Source Organization
 *
 * @Auther: LiuXing
 * @Date: 2020/02/04/17:08
 * @Description:
 */


public class HtmlTagsUtil {

    /**
     * 去除html代码中含有的标签
     * @param htmlStr
     * @return
     */
    public static String delHtmlTags(String htmlStr) {
        //定义script的正则表达式，去除js可以防止注入
        String scriptRegex="<script[^>]*?>[\\s\\S]*?<\\/script>";
        //定义style的正则表达式，去除style样式，防止css代码过多时只截取到css样式代码
        String styleRegex="<style[^>]*?>[\\s\\S]*?<\\/style>";
        //定义HTML标签的正则表达式，去除标签，只提取文字内容
        String htmlRegex="<[^>]+>";
        //定义空格,回车,换行符,制表符
        String spaceRegex = "\\s*|\t|\r|\n";

        // 过滤script标签
        htmlStr = htmlStr.replaceAll(scriptRegex, "");
        // 过滤style标签
        htmlStr = htmlStr.replaceAll(styleRegex, "");
        // 过滤html标签
        htmlStr = htmlStr.replaceAll(htmlRegex, "");
        // 过滤空格等
        htmlStr = htmlStr.replaceAll(spaceRegex, "");
        return htmlStr.trim(); // 返回文本字符串
    }

    /**
     * 获取HTML代码里的内容
     * @param htmlStr
     * @return
     */
    public static String getTextFromHtml(String htmlStr){
        //去除html标签
        htmlStr = delHtmlTags(htmlStr);
        //去除空格" "
        htmlStr = htmlStr.replaceAll(" ","");
        return htmlStr;
    }

    /***
     * 提取一段字符中的数字信息
     * @param info
     * @return
     */
    public static String getNumberInfo( String info ) {
        if( StringUtil.isNotBlank( info ) ) {
            String regEx = "[^0-9]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(info);
            String pageStr = m.replaceAll("").trim();
            return pageStr;
        }else{
            return "" ;
        }
    }

    public static void main(String[] args){
        String htmlStr= "<script type>var i=1; alert(i)</script><style> .font1{font-size:12px}</style><span>少年中国说。</span>红日初升，其道大光。<h3>河出伏流，一泻汪洋。</h3>潜龙腾渊， 鳞爪飞扬。乳 虎啸  谷，百兽震惶。鹰隼试翼，风尘吸张。奇花初胎，矞矞皇皇。干将发硎，有作其芒。天戴其苍，地履其黄。纵有千古，横有" +
                "八荒。<a href=\"www.baidu.com\">前途似海，来日方长</a>。<h1>美哉我少年中国，与天不老！</h1><p>壮哉我中国少年，与国无疆！</p>";
        System.out.println(getTextFromHtml(htmlStr));
    }

}
