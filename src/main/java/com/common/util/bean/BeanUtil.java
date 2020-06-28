package com.common.util.bean;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C), 2018
 *
 * @author yn
 * @create 2018/11/27
 * @Description PO VO
 * @since 1.0.0
 */
public class BeanUtil {
  /**
   * 对象处理
   * @param poObj 处理对象
   * @param voClass 目标对象
   * @param <T>
   * @return
   */
  public static <T> T copy(Object poObj,final Class <T>voClass){
    T voObj =null;
    try {
      voObj = voClass.newInstance();
      BeanUtils.copyProperties(poObj, voObj);
      return voObj;
    } catch (InstantiationException | IllegalAccessException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   *  对象集合处理
   * @param poList 处理对象
   * @param voClass 目标对象
   * @param <T>
   * @return
   */
  public static <T> List <T> copyList(List <? extends Object> poList ,final Class <T>voClass){

    List<T> voList=new ArrayList<T>();

    T voObj =null;
    for(Object poObj:poList){
      try {
        voObj = voClass.newInstance();
        BeanUtils.copyProperties(poObj, voObj);
        voList.add(voObj);
      } catch (InstantiationException | IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    return voList;
  }
}