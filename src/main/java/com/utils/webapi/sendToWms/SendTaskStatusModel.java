package com.utils.webapi.sendToWms;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 回传执行结果
 * @author Administrator
 *
 */
public class SendTaskStatusModel extends SendToWmsCore{
    @JsonProperty("WmsTaskId")
    public String WmsTaskId;//WMS任务编号
    @JsonProperty("TaskStatus")
    public String TaskStatus;//任务状态（开始、中断、完成）
    @JsonProperty("OutNo")
    public String OutNo;//出库任务时,出库口坐标（/"B-0-9-24"/"B-0-7-24"）
    @JsonProperty("WmsTaskId")
    public String getWmsTaskId() {
        return WmsTaskId;
    }
    @JsonProperty("WmsTaskId")
    public void setWmsTaskId(String wmsTaskId) {
        WmsTaskId = wmsTaskId;
    }
    @JsonProperty("TaskStatus")
    public String getTaskStatus() {
        return TaskStatus;
    }
    @JsonProperty("TaskStatus")
    public void setTaskStatus(String taskStatus) {
        TaskStatus = taskStatus;
    }
    @JsonProperty("OutNo")
    public String getOutNo() {
        return OutNo;
    }
    @JsonProperty("OutNo")
    public void setOutNo(String outNo) {
        OutNo = outNo;
    }
}
