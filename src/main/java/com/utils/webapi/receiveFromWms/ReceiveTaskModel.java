package com.utils.webapi.receiveFromWms;
/**
 * 接收任务
 * @author Administrator
 *
 */
public class ReceiveTaskModel {
    private String wmsTaskId;//WMS任务编号
    private String groupId;//任务组号
    private Integer taskType;//任务类型
    public String getWmsTaskId() {
		return wmsTaskId;
	}
	public void setWmsTaskId(String wmsTaskId) {
		this.wmsTaskId = wmsTaskId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public Integer getTaskType() {
		return taskType;
	}
	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}
	public String getFromArea() {
		return fromArea;
	}
	public void setFromArea(String fromArea) {
		this.fromArea = fromArea;
	}
	public String getStartNode() {
		return startNode;
	}
	public void setStartNode(String startNode) {
		this.startNode = startNode;
	}
	public String getToArea() {
		return toArea;
	}
	public void setToArea(String toArea) {
		this.toArea = toArea;
	}
	public String getEndNode() {
		return endNode;
	}
	public void setEndNode(String endNode) {
		this.endNode = endNode;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	private String fromArea;//起始库区
    private String startNode;//起始坐标
    private String toArea;//终点库区
	private String endNode;//终点坐标
    private String barCode;//容器编号(托盘码)
    private String priority;//优先级
}


