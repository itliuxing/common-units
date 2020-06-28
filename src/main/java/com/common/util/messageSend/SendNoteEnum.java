package com.common.util.messageSend;

/**
 * @Class SendNoteEnum
 * @Author 作者姓名:刘兴
 * @Version 1.0
 * @Date 创建时间：2019-07-26 10:57
 * @Copyright Copyright by wisestar company
 * @Direction 类说明
 */
public enum SendNoteEnum {



    SendNoteEnum_3( -3 , "短信数量不足" ) ,
    SendNoteEnum_4( -4 , "手机号格式不正确" ) ,
    SendNoteEnum_6( -6 , "IP限制" ) ,
    SendNoteEnum_11( -11 , "该用户被禁用" ) ,
    SendNoteEnum_14( -14 , "短信内容出现非法字符" ) ,
    SendNoteEnum_21( -21 , "MD5接口密钥加密不正确" ) ,
    SendNoteEnum_41( -41 , "手机号码为空" ) ,
    SendNoteEnum_42( -42 , "短信内容为空" ) ,
    SendNoteEnum_51( -51 , "短信签名格式不正确" ),
    SendNoteEnum_52( -52 , "短信签名太长:限10字符" );


    SendNoteEnum() {}

    SendNoteEnum( int status , String value ){
        this.status = status ;
        this.value = value ;
    }

    public String value ;
    public int status ;

    /***
     * 获取枚举的值
     * @param status
     * @return
     */
    public static String returnValue(int status){
        for ( SendNoteEnum code : SendNoteEnum.values() ) {
            if ( code.status == status ) {
                return code.value;
            }
        }
        return null ;
    }

    //覆盖方法
    @Override
    public String toString(){
        return this.status+"-"+this.value;
    }

}
