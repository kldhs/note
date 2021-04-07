package com.utils.webapi.receiveFromWms;

import java.util.List;

public class TaskHead {
	private String msgCode;
	private String msgTime;
	private List<ReceiveTaskModel> taskVal;

	public String getMsgTime() {
		return msgTime;
	}
	public void setMsgTime(String msgTime) {
		this.msgTime = msgTime;
	}
	public List<ReceiveTaskModel> getTaskVal() {
		return taskVal;
	}
	public void setTaskVal(List<ReceiveTaskModel> taskVal) {
		this.taskVal = taskVal;
	}
	public String getMsgCode() {
		return msgCode;
	}
	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}
}

