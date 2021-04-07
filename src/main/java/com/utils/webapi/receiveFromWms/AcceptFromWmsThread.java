package com.utils.webapi.receiveFromWms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.suray.entity.GridDB;
import com.suray.entity.TaskDB;
import com.suray.entity.TaskDB.TaskProgress;
import com.suray.entity.TaskDB.TaskRgvProgress;
import com.suray.enums.Connection;
import com.suray.enums.MsgCodeWithWms;
import com.suray.enums.SysParam;
import com.suray.plc.modBusOper.StationOper;
import com.suray.route.core.Node;
import com.suray.route.util.ControllerUtil;
import com.suray.route.util.LogUtil;
import com.suray.route.util.SpringUtils;
import com.suray.service.TaskService;
import com.suray.task.handle.TaskSplit;
import com.suray.wcs.inital.SystemInit;
import com.suray.wcs.pojo.Rgv;
import com.suray.wms.sendToWms.SendCarInfoToWmsModel;
import com.suray.wms.sendToWms.SendTaskStatusModel;
import com.suray.wms.sendToWms.SendToWmsCore;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public  class AcceptFromWmsThread extends Thread {
	private final static TaskService taskService = (TaskService) SpringUtils.getBean("taskService");
	private Socket socket;
	private static int adc = 1;
	  private static boolean startFlag = false;

	AcceptFromWmsThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			InputStreamReader is = new InputStreamReader(socket.getInputStream());
			char[] bs = new char[2048];
			PrintStream out;
			out = new PrintStream(socket.getOutputStream());
			StringBuilder msg = new StringBuilder();
			//			socket.setSoTimeout(10);// 如果10毫秒还没有数据，则视同没有新的数据了。因为有Keep-Alive的缘故，浏览器可能不主动断开连接的。
			socket.setSoTimeout(100); // 实际应用，会根据协议第一行是GET还是 POST确定。
			int len = -1;
			try {
				while ((len = is.read(bs)) != -1) {
					msg.append(bs, 0, len);
					msg.append("\n");
				}
			} catch (Exception e) {
				//e.printStackTrace();
				System.out.println("接收WMS信息完成");
			}
			String msg_str = msg.toString();
//			LogUtil.info("接收到WMS的信息为：\n" + msg_str);
			String result = handlerForWms(msg_str);
			out.println("HTTP/1.1 200 OK"); // 1、输出响应头信息
			out.println("Content-Type:text/html;charset:UTF-8");
			out.println();
			out.println(result);// 2、输出主页信息
			out.flush();
			out.close();
			is.close();
			socket.close(); // 关闭连接
			System.out.println("关闭与WMS连接**********************************" + System.currentTimeMillis());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String handlerForWms(String msg_str) {
//		System.out.println("handlerForWms()处理函数*****************************");
		String result = "";
		if (msg_str == null || msg_str.length() == 0) {
			return "";
		}
		String url = getUrl(msg_str);
		String jsonMsg = getJsonMsg(msg_str).replace(" ", "");
//		System.out.println("接收到的URL:\n" + url);
//		System.out.println("接收到的Json字符串:\n" + jsonMsg);
		LogUtil.info("接收到WMS的信息为：\n" + jsonMsg);
		if (jsonMsg != null && jsonMsg.length() != 0) {//有请求信息
			String msgCode = SendToWmsCore.getMsgCode(jsonMsg);//获取该信息类型
			if (msgCode.equals(MsgCodeWithWms.RgvInfo.toString())) {//Wms请求小车信息
				result = retMsg(SysParam.Wms_ReturnMsg_Ok);
				handlerForRequestRgvInfo(jsonMsg);
			} else if (msgCode.equals(MsgCodeWithWms.reciveTask.toString())) {//Wms下发任务
				if (startFlag==true) {
					handlerForIssueTask(jsonMsg);
					result = retMsg(SysParam.Wms_ReturnMsg_Ok);
				} else {
                    LogUtil.info("停止接收WMS下发的任务");
                    try {
                        Thread.sleep(8000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

			} else if (msgCode.equals(MsgCodeWithWms.Unsuccessful.toString())) {//入库申请失败
				handlerUnsuccessful(jsonMsg);
				result = retMsg(SysParam.Wms_ReturnMsg_Ok);
			} else if (msgCode.equals(MsgCodeWithWms.LineStatus.toString())) {//Wms请求输送线状态

				result = handlerForLineStatus(jsonMsg);
			} else if (msgCode.equals(MsgCodeWithWms.CancelTask.toString())) {//Wms请求任务取消
				result = handlerForCancelTask(jsonMsg);

			}else {
				//TODO 消息解析失败
				result = retMsg("AnalyticFailure");
			}
		} else {//无请求信息
			result = retMsg("NoMessage");
		}
		return result;
	}

	private static String retMsg(String msg) {
		String ret= "";
		try {
			ObjectMapper mapper = new ObjectMapper();
			SendResultModel sendResultModel = new SendResultModel();
			sendResultModel.setMsgCode(MsgCodeWithWms.ResultAnalysisModel+"");
			sendResultModel.setMsgTime(System.currentTimeMillis()+"");
			sendResultModel.setReturnMsg(msg);
			ret = mapper.writeValueAsString(sendResultModel);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}


	/**
	 * 处理Wms请求小车信息
	 *
	 * @param msg_str
	 */
	private static void handlerForRequestRgvInfo(String msg_str) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			ReceiveCarInfoModel acceptCarInfoPoJo = mapper.readValue(msg_str, ReceiveCarInfoModel.class);
			Rgv rgv = SystemInit.getRgvByDeviceNo(Integer.valueOf(acceptCarInfoPoJo.getDeviceNo()));

			//发送设备信息
			SendCarInfoToWmsModel sendWmsCarInfoPoJo = new SendCarInfoToWmsModel();
			sendWmsCarInfoPoJo.setMsgCode(MsgCodeWithWms.RgvInfo.toString());
			sendWmsCarInfoPoJo.setMsgTime(String.valueOf(System.currentTimeMillis()));
			sendWmsCarInfoPoJo.setDeviceNo(acceptCarInfoPoJo.getDeviceNo());
			sendWmsCarInfoPoJo.setLocation(rgv.getLocation().toString());
			sendWmsCarInfoPoJo.setElectric(rgv.getBattery().toString());
			sendWmsCarInfoPoJo.setStatus(rgv.getRgvStatus().toString());
			sendWmsCarInfoPoJo.send(sendWmsCarInfoPoJo);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 入库任务申请失败
	 * @param msg_str
	 */
	private static void handlerUnsuccessful(String msg_str) {
		ObjectMapper mapper = new ObjectMapper();
		try {

			ReceiveTaskErrorModel receiveTaskErrorModel = mapper.readValue(msg_str, ReceiveTaskErrorModel.class);
			String localhost = receiveTaskErrorModel.getStartNode();
			//TODO 输送线回退

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		//	System.out.println(getNodeStrFromGridId("B-0-0-0"));
		String json4 = "{\"msgCode\":\"reciveTask\",\"msgTime\":\"20190521145731\",\"taskVal\":[{\"wmsTaskId\":\"DST20190521000001\",\"groupId\":\"IN201905210001\",\"taskType\":\"1\",\"fromArea\":\"AT001-A\",\"startNode\":\"A-0-15-37\",\"toArea\":\"AT001-B\",\"endNode\":\"B-2-8-21\",\"barCode\":\"YZX00000126\",\"priority\":\"2\"}]}";
//		String json5 = "{'msgCode':'ReciveTask','msgTime':'10190519120000','taskVal':[{'wmsTaskId':'DST2019051900001','groupId':'OUT_TASK2019061900001','taskType':'3','fromArea':'AT001-A','startNode':'A-1-16-6','toArea':'AT001-A','endNode':'A-1-15-8','barCode':'354675883','priority':'4'},{'wmsTaskId':'DST2019051900001','groupId':'OUT_TASK2019061900001','taskType':'3','fromArea':'AT001-A','startNode':'A-1-16-6','toArea':'AT001-A','endNode':'A-1-15-8','barCode':'354675883','priority':'4'}]}";
		handlerForIssueTask(json4);
	}
	/**
	 * 处理Wms下发任务
	 *
	 * @param msg_str
	 */
	private static void handlerForIssueTask(String msg_str) {
		System.err.println("处理wms下发的任务。。。。。。。。。。。。。。。。。。。。。。。。");
		JSONObject jsonObject=JSONObject.fromObject(msg_str);
		Map<String, Class> map = new HashMap<String, Class>();
		map.put("taskVal", ReceiveTaskModel.class);
		TaskHead taskHead = (TaskHead) JSONObject.toBean(jsonObject, TaskHead.class, map);
		for (int i = 0; i < taskHead.getTaskVal().size(); i++) {
			TaskDB taskDB = new TaskDB();
			taskDB.setWmsTaskId(taskHead.getTaskVal().get(i).getWmsTaskId());
			taskDB.setGroupId(taskHead.getTaskVal().get(i).getGroupId());
			taskDB.setTaskType(taskHead.getTaskVal().get(i).getTaskType()-1);
			taskDB.setStartNodeStr(getNodeStrFromGridId(taskHead.getTaskVal().get(i).getStartNode()));
			taskDB.setEndNode(Node.parseGrid(getNodeStrFromGridId(taskHead.getTaskVal().get(i).getEndNode())));
			taskDB.setStartNode(Node.parseGrid(getNodeStrFromGridId(taskHead.getTaskVal().get(i).getStartNode())));
			taskDB.setBarCode(taskHead.getTaskVal().get(i).getBarCode());
			taskDB.setSortCode(Integer.valueOf(taskHead.getTaskVal().get(i).getPriority()));
			taskDB.setTaskProgress(TaskProgress.NEW.getProgressNum());
			taskDB.setRgvProgress(TaskRgvProgress.NOT_ASSIGNED_TO_RGV.getProgressNum());
			taskDB.setConnectionType(Connection.NO_CONNECTION.getNum());

			//拆分任务，并插入数据库

//			if(taskDB.getTaskType()!=TaskDB.TaskType.OUT.getTypeNum()) {
				TaskSplit taskSplit = new TaskSplit(taskDB);
				taskSplit.taskHandle();
				retTask(taskHead.getTaskVal().get(i).getWmsTaskId());
//			}else{
//				LogUtil.info("停止接收出库任务");
//			}

		}
	}

	//接收到wms任务后立即向wms回传任务状态为接收
	public static void retTask(String taskId) {
		SendTaskStatusModel uploadTaskStatus = new SendTaskStatusModel();
		uploadTaskStatus.setMsgCode(MsgCodeWithWms.TaskStatus+"");
		uploadTaskStatus.setMsgTime(String.valueOf(System.currentTimeMillis()));
		uploadTaskStatus.setWmsTaskId(taskId);
		uploadTaskStatus.setTaskStatus(TaskProgress.NEW.getProgressNum()+"");
		uploadTaskStatus.send(uploadTaskStatus);
	}

	//向wms上传任务状态为异常
	public static void sendWmsTaskError(String taskId) {
		SendTaskStatusModel uploadTaskStatus = new SendTaskStatusModel();
		uploadTaskStatus.setMsgCode(MsgCodeWithWms.TaskStatus+"");
		uploadTaskStatus.setMsgTime(String.valueOf(System.currentTimeMillis()));
		uploadTaskStatus.setWmsTaskId(taskId);
		uploadTaskStatus.setTaskStatus(TaskProgress.ERROR.getProgressNum()+"");
		uploadTaskStatus.send(uploadTaskStatus);
	}


	/**
	 * 处理Wms请求输送线状态
	 */
	private static String handlerForLineStatus(String msg_str) {
		String result = "";
		ObjectMapper mapper = new ObjectMapper();
		try {
			ReceiveLineStatusModel receiveLineStatusModel = mapper.readValue(msg_str, ReceiveLineStatusModel.class);
			RetLineStatusToWmsModel sendWmsLineStatusPoJo = new RetLineStatusToWmsModel();
			sendWmsLineStatusPoJo.setMsgCode(MsgCodeWithWms.LineStatus.toString());
			sendWmsLineStatusPoJo.setMsgTime(String.valueOf(System.currentTimeMillis()));
			if(SysParam.ConnectOne.equals(receiveLineStatusModel.getLineCoord())){
				StationOper stationOper = StationOper.getDevice("Connect_One");
				int i =stationOper.readDataFromStation();
				sendWmsLineStatusPoJo.setStatusCode(String.valueOf(i));
			}else if(SysParam.ConnectTwo.equals(receiveLineStatusModel.getLineCoord())){
				StationOper stationOper = StationOper.getDevice("Connect_Two");
				int i =stationOper.readDataFromStation();
				sendWmsLineStatusPoJo.setStatusCode(String.valueOf(i));
			}
			result = mapper.writeValueAsString(sendWmsLineStatusPoJo);
			LogUtil.info("处理输送线状态请求"+result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 处理Wms请求取消任务
	 * @return
	 */
	private static String handlerForCancelTask(String msg_str) {
		ObjectMapper mapper = new ObjectMapper();
		String ret = "";
		try {
			ReceiveCancelTaskModel acceptCancelTaskPoJo = mapper.readValue(msg_str, ReceiveCancelTaskModel.class);
			TaskDB taskDB= new TaskDB();
			taskDB.setWmsTaskId(acceptCancelTaskPoJo.getWmsTaskId());
			taskDB= taskService.findTask(taskDB);
			if(taskDB!=null){
				if (taskDB.equals(TaskProgress.NEW.getProgressNum()) ||taskDB.equals(TaskProgress.WAIT.getProgressNum())) {
					TaskDB taskDBCancel= new TaskDB();
					taskDBCancel.setTaskProgress(TaskProgress.CANCEL.getProgressNum());
					taskService.updateTask(taskDBCancel);
				}else {
					//TODO 取消失败
					ret = retMsg("Irrevocably");
				}
			}else {
				//TODO 不存在该任务
				ret = retMsg("NotFound");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	private static String getUrl(String msg_str) {
		String begin = "POST";
		String end = "HTTP/";
		int index_start = msg_str.indexOf(begin) + begin.length();
		int index_end = msg_str.indexOf(end);
		if (index_start >= 0) {
			String temp = msg_str.substring(index_start, index_end);
			temp = temp.trim();
			return temp;
		} else {
			System.out.println("WMS:" + msg_str);
		}
		return null;
	}

	private static String getJsonMsg(String msg_str) {
		int index = msg_str.indexOf("\n\r\n");
		if (index > 0) {
			String temp = msg_str.substring(index);
			temp = temp.trim();
			return temp;
		} else {
			System.out.println("WMS:" + msg_str);
		}
		return null;
	}

	//通过Gridid获取NodeStr
	private static String getNodeStrFromGridId(String gridId) {
		GridDB gridDB =ControllerUtil.gridService.findOneByGridId(gridId);
		return  "("+gridDB.getGridX()+","+gridDB.getGridY()+","+gridDB.getGridZ()+","+gridDB.getDistrict()+",);";
	}

	 public static void setStartFlag(boolean startFlag) {
		 AcceptFromWmsThread.startFlag = startFlag;
	}
}
