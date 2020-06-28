package com.common.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Copyright (C), 2018
 *
 * @author yn
 * @create 2018/12/10
 * @Description 图片水印工具
 * @since 1.0.0
 */
public class WaterMarkUtils {
  private final static Logger logger = LoggerFactory.getLogger(WaterMarkUtils.class);
  private final static String ISO_8859_1 = "ISO-8859-1";
  private final static String GB2312 = "GB2312";
  private final static String UTF_8 = "UTF-8";
  private WaterMarkUtils(){}
  private static class WaterMarkInstance{
    private static final WaterMarkUtils WATER_MARK_UTILS = new WaterMarkUtils();
  }
  public static WaterMarkUtils getInstance(){
    return WaterMarkInstance.WATER_MARK_UTILS;
  }

  /**
   * 创建图片水印
   * @param srcImgPath 源图片路径
   * @param targetImgPath 目标图片路径
   * @param waterMarkContent 水印内容 default
   * @param alpha 水印透明度 default(0.5F)
   * @param degree 水印旋转角度
   * @param markContentColor 水印内容颜色 default(88,58,18,198)
   * @param waterMarkFont 水印字体对象 default("宋体", Font.PLAIN, 20)
   */
  public File WaterMarkCreator(String srcImgPath, String targetImgPath, String waterMarkContent, float alpha, Integer degree, Color markContentColor,Font waterMarkFont){
    FileOutputStream outImgStream = null;
    if(StringUtils.isBlank(waterMarkContent)){
      waterMarkContent = "真材实价网";
    }
    try {
      if(waterMarkContent.equals(new String(waterMarkContent.getBytes(ISO_8859_1), UTF_8))){
        waterMarkContent = new String(waterMarkContent.getBytes(ISO_8859_1), UTF_8);
      }
      if(waterMarkContent.equals(new String(waterMarkContent.getBytes(GB2312), UTF_8))){
        waterMarkContent = new String(waterMarkContent.getBytes(GB2312), UTF_8);
      }
      String postFix = null;
      /* 读取原图片信息 */
      if(StringUtils.isNotBlank(srcImgPath)){
        postFix = srcImgPath.substring(srcImgPath.lastIndexOf(".") + 1);
      }
      File srcImgFile = new File(srcImgPath);/* 得到文件 */
      Image srcImg = ImageIO.read(srcImgFile);/* 文件转化为图片 */
      int srcImgWidth = srcImg.getWidth(null);/* 获取图片的宽 */
      int srcImgHeight = srcImg.getHeight(null);/* 获取图片的高 */
      /* 加水印 */
      BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
      Graphics2D g = bufImg.createGraphics();
      g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, (alpha > 0 ? alpha : 0.5f)));
      /* 对线段的锯齿状边缘处理 */
      g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
      g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
      /* 根据图片的背景设置水印颜色 */
      if(markContentColor != null){
        g.setColor(markContentColor);
      } else{
        g.setColor(new Color(88,58,18,198));
      }
      /* 设置字体 */
      if(waterMarkFont == null){
        waterMarkFont = new Font("黑体", Font.BOLD, waterFontSize(srcImgHeight));
      }
      g.setFont(waterMarkFont);

      /* 设置水印旋转 */
      if (degree>0) {
        g.rotate(Math.toRadians(degree), (double) srcImgWidth / 2, (double) srcImgHeight / 2);
      }

      /* 设置水印的坐标 */
      /*int x = srcImgWidth - 2*getWatermarkLength(waterMarkContent, g);
      int y = srcImgHeight - 2*getWatermarkLength(waterMarkContent, g);*/
      /* 获得文字信息 */
      FontRenderContext context = g.getFontRenderContext();
      Rectangle2D bounds = waterMarkFont.getStringBounds(waterMarkContent, context);
      /* 文字自适应大小 300为基数 */
      /*waterMarkFont.deriveFont(srcImgHeight/300*20);*/
      int dx = new Double(srcImgWidth - bounds.getWidth()).intValue()/2;
      int dy = new Double(srcImgHeight - bounds.getHeight()).intValue()/2;
      /* 文字水印自动换行 */
      int charTempLen = 0;
      StringBuffer buffer = new StringBuffer();
      StringBuffer wordLength = new StringBuffer();
      for(char word : StringUtils.isNotBlank(waterMarkContent) ? waterMarkContent.toCharArray() : null){
        buffer.append(word);
        wordLength.append(word);
        charTempLen += getWatermarkLength(wordLength.toString().trim(), g);
        if(charTempLen >= srcImgWidth){
          g.drawString(buffer.toString().trim(), dx, dy);  /* 画出水印 */
          buffer.delete(0, buffer.length());
          dy+=waterMarkFont.getSize();
        }
        wordLength.delete(0, wordLength.length());
      }
      if(StringUtils.isNotBlank(buffer.toString().trim())){
        g.drawString(buffer.toString().trim(), dx, dy);  /* 画出水印 */
      }
      buffer = null; wordLength = null;
      g.dispose();
      /* 输出图片 */
      outImgStream = new FileOutputStream(targetImgPath);

      ImageIO.write(bufImg, postFix, outImgStream);
      File returnFile = new File(targetImgPath);

      return returnFile;
    } catch (FileNotFoundException fileNotFound) {
      logger.info("<水印>文件找不到." + fileNotFound.getMessage());
      fileNotFound.printStackTrace();
    } catch (IOException ie){
      logger.info("<水印>文件异常." + ie.getMessage());
      ie.printStackTrace();
    } catch (Exception e){
      logger.info("<水印>异常:" + e.getMessage());
      e.printStackTrace();
    } finally {
      try {
        outImgStream.flush();
        outImgStream.close();
      } catch (IOException ioe){
        ioe.printStackTrace();
        logger.info("<水印>释放异常:" + ioe.getMessage());
      }
    }
    return null;
  }

  /**
   * 获得文字大小
   * @param waterMarkContent
   * @param g
   * @return
   */
  protected int getWatermarkLength(String waterMarkContent, Graphics2D g) {
    return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
  }

  /**
   * 删除单个文件
   * @param targetFile 目标文件
   * @return
   */
  protected boolean deleteFile(final File targetFile){
    /* 删除原来文件 */
    if (targetFile.exists() && targetFile.isFile()) {
      targetFile.delete();
      return true;
    }
    logger.info("文件删除失败.");
    return false;
  }

  /**
   * 水印大小自适应
   * @param imgHeight 图片大小
   * @return
   */
  protected int waterFontSize(final Integer imgHeight){
    int fontSize = 10;
    if(imgHeight > 0 && imgHeight <= 300){
      fontSize = 20;
    } else if(imgHeight > 300 && imgHeight <= 800){
      fontSize = 40;
    } else if(imgHeight > 800 && imgHeight <= 1500){
      fontSize = 60;
    } else if(imgHeight > 1500 && imgHeight <= 2500){
      fontSize = 90;
    } else if(imgHeight > 2500 && imgHeight <= 3500){
      fontSize = 110;
    } else if(imgHeight > 3500){
      fontSize = 140;
    }
    return fontSize;
  }
}