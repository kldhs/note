package com.utils.webapi.receiveFromWms;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 设备信息获取(WMS->WCS)
 * @author Administrator
 *
 */
public class ReceiveCarInfoModel {
    @JsonProperty("MsgCode")
    private String MsgCode;//消息类型(RgvInfo)
    @JsonProperty("MsgTime")
    private String MsgTime;//时间yyyyMMddhhmmss
    @JsonProperty("DeviceNo")
    private String DeviceNo;//设备编号

    @JsonProperty("MsgCode")
    public String getMsgCode() {
        return MsgCode;
    }
    @JsonProperty("MsgCode")
    public void setMsgCode(String msgCode) {
        MsgCode = msgCode;
    }
    @JsonProperty("MsgTime")
    public String getMsgTime() {
        return MsgTime;
    }
    @JsonProperty("MsgTime")
    public void setMsgTime(String msgTime) {
        MsgTime = msgTime;
    }
    @JsonProperty("DeviceNo")
    public String getDeviceNo() {
        return DeviceNo;
    }
    @JsonProperty("DeviceNo")
    public void setDeviceNo(String deviceNo) {
        DeviceNo = deviceNo;
    }
}

