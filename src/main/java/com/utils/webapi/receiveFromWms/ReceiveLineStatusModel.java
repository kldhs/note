package com.utils.webapi.receiveFromWms;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 输送线状态请求（WMS->WCS）
 * @author Administrator
 *
 */
public class ReceiveLineStatusModel {
	@JsonProperty("MsgCode")
	private String MsgCode;
	@JsonProperty("LineCoord")
	private String LineCoord;
	public String getMsgCode() {
		return MsgCode;
	}
	public void setMsgCode(String msgCode) {
		MsgCode = msgCode;
	}
	public String getLineCoord() {
		return LineCoord;
	}
	public void setLineCoord(String lineCoord) {
		LineCoord = lineCoord;
	}
	public String getMsgTime() {
		return MsgTime;
	}
	public void setMsgTime(String msgTime) {
		MsgTime = msgTime;
	}
	@JsonProperty("MsgTime")
	private String MsgTime;
}
