package com.isoftstone.app.base.common;

public class OperationPrompt 
{
	private boolean success;//返回是否成功
	private String msg;
	
	public OperationPrompt() {
		this(null, true);
	}
	
	public OperationPrompt(String msg) {
		this(null, true);
	}
	
	public OperationPrompt(boolean success) {
		this(null, success);
	}
	
	public OperationPrompt(String msg, boolean success) {
		this.success = success;
		this.msg = msg;
	}

	public boolean isSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
