package com.utils.webapi.sendToWms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suray.enums.MsgCodeWithWms;
import com.suray.enums.SysParam;
import com.suray.route.util.LogUtil;

import java.io.IOException;

public class AcceptFromWmsCore {

	public static void main(String[] args) {
		String str ="{\"MsgCode\": \"ResultAnalysisModel\",\"MsgTime\":\"20190519140957\",\"ReturnMsg\":\"无该托盘的入库任务\"}";
		analysisJson(str);
	}
	public static boolean analysisJson(String result) {
		String msgCode = SendToWmsCore.getMsgCode(result.replace(" ", ""));
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			if (msgCode.equals(MsgCodeWithWms.ResultAnalysisModel.toString())) {//上传异常信息
				ResultAnalysisModel resultAnalysisModel = objectMapper.readValue(result, ResultAnalysisModel.class);
				return handlerResultMsg(resultAnalysisModel);
			} else {
				LogUtil.error("");
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	private static boolean handlerResultMsg(ResultAnalysisModel handlerResultMsg) {
		if(handlerResultMsg.getReturnMsg().equals(SysParam.Wms_ReturnMsg_Ok)){
			return true;
		}else {
			return false;
		}
	}
}
