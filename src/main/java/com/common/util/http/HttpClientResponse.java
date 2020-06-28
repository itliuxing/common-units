package com.common.util.http;

import org.apache.http.Header;

import java.io.Serializable;

/**
 * @Class HttpClientResponse
 * @Author 作者姓名:刘兴
 * @Version 1.0
 * @Date 创建时间：2019-04-23 15:11
 * @Copyright Copyright by wisestar company
 * @Direction 类说明
 */
public class HttpClientResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String body ;
	private Header[] headers ;
	private String reasonPhrase ;
	private int statusCode ;
	
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public Header[] getHeaders() {
		return headers;
	}
	public void setHeaders(Header[] headers) {
		this.headers = headers;
	}
	public String getReasonPhrase() {
		return reasonPhrase;
	}
	public void setReasonPhrase(String reasonPhrase) {
		this.reasonPhrase = reasonPhrase;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	

	
	

}
