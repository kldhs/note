package com.utils.webapi.sendToWms;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 异常信息上报
 * @author Administrator
 */
public class SendExceptionInfoModel extends SendToWmsCore{
	@JsonProperty("WmsTaskId")
	public String WmsTaskId;//WMS任务编号
	@JsonProperty("DeviceId")
	public String DeviceId;//设备编号
	@JsonProperty("ErrorMsg")
	public String ErrorMsg;//异常原因
	@JsonProperty("ErrorLocal")
	public String ErrorLocal;//异常位置
	@JsonProperty("BarCode")
	public String BarCode;//条码信息

	@JsonProperty("BarCode")
	public String getBarCode() {
		return BarCode;
	}
	@JsonProperty("BarCode")
	public void setBarCode(String barCode) {
		BarCode = barCode;
	}
	@JsonProperty("WmsTaskId")
	public String getWmsTaskId() {
		return WmsTaskId;
	}
	@JsonProperty("WmsTaskId")
	public void setWmsTaskId(String wmsTaskId) {
		WmsTaskId = wmsTaskId;
	}
	@JsonProperty("DeviceId")
	public String getDeviceId() {
		return DeviceId;
	}
	@JsonProperty("DeviceId")
	public void setDeviceId(String deviceId) {
		DeviceId = deviceId;
	}
	@JsonProperty("ErrorMsg")
	public String getErrorMsg() {
		return ErrorMsg;
	}
	@JsonProperty("ErrorMsg")
	public void setErrorMsg(String errorMsg) {
		ErrorMsg = errorMsg;
	}
	@JsonProperty("ErrorLocal")
	public String getErrorLocal() {
		return ErrorLocal;
	}
	@JsonProperty("ErrorLocal")
	public void setErrorLocal(String errorLocal) {
		ErrorLocal = errorLocal;
	}
}
