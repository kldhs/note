package com.utils.webapi.receiveFromWms;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 入库任务申请失败
 * @author Administrator
 *
 */
public class ReceiveTaskErrorModel {
	@JsonProperty("MsgCode")
	private String MsgCode;//消息类型(ReciveTask)
	@JsonProperty("MsgTime")
	private String MsgTime;//返回时间yyyyMMddhhmmss
	@JsonProperty("BarCode")
	private String BarCode;//条码
	@JsonProperty("StartNode")
	private String StartNode;//位置
	@JsonProperty("ErrorMsg")
	private String ErrorMsg;//位置
	public String getMsgCode() {
		return MsgCode;
	}
	public void setMsgCode(String msgCode) {
		MsgCode = msgCode;
	}
	public String getMsgTime() {
		return MsgTime;
	}
	public void setMsgTime(String msgTime) {
		MsgTime = msgTime;
	}
	public String getBarCode() {
		return BarCode;
	}
	public void setBarCode(String barCode) {
		BarCode = barCode;
	}
	public String getStartNode() {
		return StartNode;
	}
	public void setStartNode(String startNode) {
		StartNode = startNode;
	}
	public String getErrorMsg() {
		return ErrorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		ErrorMsg = errorMsg;
	}
}
