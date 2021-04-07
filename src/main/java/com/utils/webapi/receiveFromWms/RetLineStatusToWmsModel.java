package com.utils.webapi.receiveFromWms;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 输送线状态发送（WCS->WMS）
 * @author Administrator
 *
 */
public class RetLineStatusToWmsModel {
    @JsonProperty("MsgCode")
    private String MsgCode;//消息类型(LineStatus)
    @JsonProperty("MsgTime")
    private String MsgTime;//返回时间yyyyMMddhhmmss
    @JsonProperty("StatusCode")
    private String StatusCode;//状态码: 1-有货;0-无货

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

    public String getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(String statusCode) {
        StatusCode = statusCode;
    }
}
