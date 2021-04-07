package com.utils.webapi.sendToWms;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 入库申请
 * @author Administrator
 *
 */
public class SendAskForInStrockModel extends SendToWmsCore {

    @JsonProperty("BarCode")
    private String BarCode;//容器编号（托盘码）
    @JsonProperty("Height")
    private String Height;//高度(90-130)
    @JsonProperty("StartNode")
    private String StartNode;//入库口（起始位置）

    @JsonProperty("BarCode")
    public String getBarCode() {
        return BarCode;
    }

    @JsonProperty("BarCode")
    public void setBarCode(String barCode) {
        BarCode = barCode;
    }

    @JsonProperty("Height")
    public String getHeight() {
        return Height;
    }

    @JsonProperty("Height")
    public void setHeight(String height) {
        Height = height;
    }

    @JsonProperty("StartNode")
    public String getStartNode() {
        return StartNode;
    }

    @JsonProperty("StartNode")
    public void setStartNode(String startNode) {
        StartNode = startNode;
    }
}
