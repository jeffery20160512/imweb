package com.isoftstone.app.base.common;


public class Result extends OperationPrompt{
	
	private String content;//消息内容
	private String msgCode;//消息编码 由6位数字组成，“000000”表示成功，“999999”表示未知异常
	
	public Result(){}
    
	public Result(boolean success,String msg, String content, String msgCode) {
		this.setSuccess(success);
		this.setMsg(msg);
		this.setContent(content);
		this.setMsgCode(msgCode);
	}
	
	public Result(boolean success,String msg){
		this(success, msg,null,null);
	}
	
	public Result(boolean success,String msg, String content) {
		this(success, msg, content, null);
	}
	
	//public Result(boolean success,String msg, String msgCode) {
	//	this(success, msg, null, msgCode);
	//}
	
	public String getMsgCode() {
		return msgCode;
	}
	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
