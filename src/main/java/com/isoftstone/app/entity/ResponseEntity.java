package com.isoftstone.app.entity;

public class ResponseEntity {
    /** 操作结果 */
    public Integer rcode;
    
    /** 响应消息 */
    public String resultMsg;

    public ResponseEntity(int rcode, String resultMsg) {
		this.rcode = rcode;
		this.resultMsg = resultMsg;
	}
    
	public Integer getRcode() {
		return rcode;
	}

	public void setRcode(Integer rcode) {
		this.rcode = rcode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
}
