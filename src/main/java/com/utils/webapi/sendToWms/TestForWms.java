package com.utils.webapi.sendToWms;
import com.suray.enums.MsgCodeWithWms;

public class TestForWms {


	public static void main(String[] args) {
		//		String str ="{}";
		//		String str ="{"MsgCode":"TaskStatus","MsgTime":"1558250235095","WmsTaskId":"11234t567","TaskStatus":"65"}";
		////		String url="http://192.168.165.10:8088/api/Fwcs/AskForInStock";
		//		String url="http://192.168.165.10:8088/api/Fwcs/RgvInfo";

		//		String url="http://192.168.165.10:8088/api/Fwcs/TaskStatus";
		//				SendToWmsCore.sendPost(url, str);



		//				String url = "http://192.168.165.10:8088/api/Fwcs/AskForInStock";
		//				String param = "{}";
		//				SendToWmsCore.sendPost(url, param);



		//入库申请
		SendAskForInStrockModel askForInStock = new SendAskForInStrockModel();
		askForInStock.setMsgCode(MsgCodeWithWms.AskForInStock+"");
		askForInStock.setBarCode("12342332");
		askForInStock.setHeight("130");
		askForInStock.setStartNode("1");
		askForInStock.send(askForInStock);



		//		//设备信息发送
		//		String deviceId = "1";
		//		String location ="123";
		//		String battery ="23";
		//		String rgv = "";
		//
		//		SendCarInfoToWmsModel sendWmsCarInfoPoJo = new SendCarInfoToWmsModel();
		//		sendWmsCarInfoPoJo.setMsgCode(MsgCodeWithWms.RgvInfo+"");
		//		sendWmsCarInfoPoJo.setMsgTime(String.valueOf(System.currentTimeMillis()));
		//		sendWmsCarInfoPoJo.setDeviceNo(deviceId);
		//		sendWmsCarInfoPoJo.setLocation(location);
		//		sendWmsCarInfoPoJo.setElectric(battery);
		//		sendWmsCarInfoPoJo.setStatus(rgv);
		//		sendWmsCarInfoPoJo.send(sendWmsCarInfoPoJo);
		//
		//
		//
		//		//回传执行结果
		//		SendTaskStatusModel uploadTaskStatus = new SendTaskStatusModel();
		//		uploadTaskStatus.setMsgCode(MsgCodeWithWms.TaskStatus+"");
		//		uploadTaskStatus.setMsgTime(String.valueOf(System.currentTimeMillis()));
		//		uploadTaskStatus.setWmsTaskId("11234t567");
		//		uploadTaskStatus.setTaskStatus(TaskProgress.VEHICLE_OVER.getProgressNum()+"");
		//		uploadTaskStatus.send(uploadTaskStatus);
		//
		//
		//		//上报异常
		//		SendExceptionInfoModel uploadExceptionInfo = new SendExceptionInfoModel();
		//		uploadExceptionInfo.setMsgCode(MsgCodeWithWms.ExceptionMsg+"");
		//		uploadExceptionInfo.setMsgTime(System.currentTimeMillis()+"");
		//		uploadExceptionInfo.setDeviceId("33");
		//		uploadExceptionInfo.setErrorLocal("33");
		//		uploadExceptionInfo.setErrorMsg("fff");
		//		uploadExceptionInfo.setWmsTaskId("11");
		//		uploadExceptionInfo.send(uploadExceptionInfo);



		//		String[] zoneArr={"1","4","7","10","13","16"};
		//		for (int i = 1; i <17 ; i++) {
		//			System.out.println(Arrays.binarySearch(zoneArr,String.valueOf(i)));
		//		}
		//
		//		int[] zoneArr1={1,4,7,10,13,16};
		//		for (int i = 1; i <17 ; i++) {
		//			System.out.println(Arrays.binarySearch(zoneArr1,i));
		//		}
		//
		//		String[] taskTypeArr = ",".split(",");
		//		System.out.println(taskTypeArr);
	}
}
