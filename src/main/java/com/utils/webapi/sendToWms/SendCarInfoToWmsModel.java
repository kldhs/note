package com.utils.webapi.sendToWms;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 设备信息发送（WCS->WMS）
 * @author Administrator
 *
 */
public class SendCarInfoToWmsModel extends SendToWmsCore {
    @JsonProperty("MsgCode")
    private String MsgCode;//消息类型(RgvInfo)
    @JsonProperty("MsgTime")
    private String MsgTime;//返回时间yyyyMMddhhmmss
    @JsonProperty("DeviceNo")
    private String DeviceNo;//设备编号
    @JsonProperty("Electric")
    private String Electric;//电量
    @JsonProperty("Status")
    private String Status;//小车状态
    @JsonProperty("Location")
    private String Location;//小车位置

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
    @JsonProperty("Electric")
    public String getElectric() {
        return Electric;
    }
    @JsonProperty("Electric")
    public void setElectric(String electric) {
        Electric = electric;
    }
    @JsonProperty("Status")
    public String getStatus() {
        return Status;
    }
    @JsonProperty("Status")
    public void setStatus(String status) {
        Status = status;
    }
    @JsonProperty("Location")
    public String getLocation() {
        return Location;
    }
    @JsonProperty("Location")
    public void setLocation(String location) {
        Location = location;
    }
}
