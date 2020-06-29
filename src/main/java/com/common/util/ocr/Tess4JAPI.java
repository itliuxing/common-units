package com.common.util.ocr;


import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

/**
 * @Class Tess4JAPI
 * @Author 作者姓名:刘兴
 * @Version 1.0
 * @Date 创建时间：2020-06-12 16:28
 * @Copyright Copyright by wisestar company
 * @Direction 类说明
 */
public class Tess4JAPI {

    public static void main(String[] args){
        //需要识别的图片地址
        String path = "G:\\360MoveData\\Users\\Administrator\\Desktop\\Tess4jOCRAPI\\163051815_90256.png" ;
        File remoteFile = new File( path );
        //File remoteFile = new File("http://gcj-statics.oss-cn-beijing.aliyuncs.com/imageprice/116573/239600548_116573.png");
        if( !remoteFile.exists() ){
            System.out.println( "图片不存在" );
            return ;
        }
        Tesseract instance = new Tesseract() ;
        //设置训练库的位置
        instance.setDatapath( System.getenv("TESSDATA_PREFIX") );//设置训练库的位置
        instance.setLanguage("eng");//chi_sim ：简体中文， eng	根据需求选择语言库
        String result = null;
        try {
            long startTime = System.currentTimeMillis();
            result =  instance.doOCR( remoteFile );
            long endTime = System.currentTimeMillis();
            System.out.println("Time is：" + (endTime - startTime) + " 毫秒");
        } catch (TesseractException e) {
            e.printStackTrace();
        }

        System.out.println("result: ");
        System.out.println(result);
    }

}


