package com.utils.webapi.sendToWms;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 发送结果处理
 * @author Administrator
 *
 */
public class ResultAnalysisModel extends SendToWmsCore{
	@JsonProperty("MsgCode")
	public String MsgCode;//消息类型
	@JsonProperty("ReturnMsg")
	public String ReturnMsg;//接收结果
	@JsonProperty("MsgTime")
	public String MsgTime;//接收时间
	public String getMsgCode() {
		return MsgCode;
	}
	public void setMsgCode(String msgCode) {
		MsgCode = msgCode;
	}
	public String getReturnMsg() {
		return ReturnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		ReturnMsg = returnMsg;
	}
	public String getMsgTime() {
		return MsgTime;
	}
	public void setMsgTime(String msgTime) {
		MsgTime = msgTime;
	}

}
