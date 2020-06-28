package com.common.util.messageSend;

import com.common.util.http.HttpSSLClient;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @Class WechatRefundWechatThread
 * @Author 作者姓名:刘兴
 * @Version 1.0
 * @Date 创建时间：2020-06-16 11:07
 * @Copyright Copyright by wisestar company
 * @Direction 类说明       微信退款-微信消息发送线程
 */
public class WechatRefundWechatThread extends Thread {

    private String first;
    private String remark;

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public WechatRefundWechatThread(String first, String remark) {
        this.first = first;
        this.remark = remark;
    }

    public WechatRefundWechatThread() {
    }

    @Override
    public void run() {
        try{
            //停止十秒材发送模板消息
            Thread.sleep(1000);
            List<String> openIds = new ArrayList<>();
            openIds.add( "oc5y8vsPC6myVxk7wEcY3u2YMqys" ) ;
            /*openIds.add( "oc5y8vqqFtXYteVd9LUe3shUO67k" ) ;
            openIds.add( "oc5y8vgiQLh16594D4CqAwf2SKm8" ) ;*/
            for(int i=0 ; i< openIds.size() ; i++){
                String openid = openIds.get(i);
                JSONObject param= new JSONObject();
                JSONObject inmap= new JSONObject();
                JSONObject firstJson= new JSONObject();
                firstJson.put("value",first);
                JSONObject remarkJson= new JSONObject();
                remarkJson.put("value",remark);
                inmap.put("openid",openid);
                inmap.put("templateid", wechatTemplateConf.commonTemplateid  );
                inmap.put("first","\""+firstJson.toString()+"\"");
                inmap.put("remark","\""+remarkJson.toString()+"\"");
                param.put("inmap",inmap);
                System.out.println(param.toString()+"------------");
                String webResultInfoJson = HttpSSLClient.doPost( wechatTemplateConf.commonSendUrl  ,param.toString(),"utf-8" );
                JSONObject jSONObject = JSONObject.fromObject(webResultInfoJson);
                if(jSONObject.get("success").equals(false)){
                    System.out.println("发送微信模板不成功");
                    System.out.println(webResultInfoJson);
                }
            }
        }catch (Exception e){
            System.out.println("发送微信模板异常");
            e.printStackTrace();
        }
    }
}
