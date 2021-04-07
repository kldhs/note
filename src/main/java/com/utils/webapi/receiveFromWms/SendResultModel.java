package com.utils.webapi.receiveFromWms;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 接收结果发送
 * @author Administrator
 *
 */
public class SendResultModel {
    @JsonProperty("MsgCode")
    private String MsgCode;//消息类型(RgvInfo)
    @JsonProperty("MsgTime")
    private String MsgTime;//时间yyyyMMddhhmmss
    @JsonProperty("ReturnMsg")
    private String ReturnMsg;//发送结果
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
	public String getReturnMsg() {
		return ReturnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		ReturnMsg = returnMsg;
	}

}

