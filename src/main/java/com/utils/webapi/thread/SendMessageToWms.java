package com.utils.webapi.thread;


import com.utils.logutil.LogUtil;
import com.utils.webapi.sendToWms.SendExceptionInfoModel;
import com.utils.webapi.sendToWms.SendTaskStatusModel;

/**
 * 发送任务状态
 */
public class SendMessageToWms extends Thread {
    //任务db
    private TaskDB taskDB;
    //消息类型
    private MsgCodeWithWms msgCodeWithWms;
    //任务进程
    private TaskDB.TaskProgress taskProgress;
    //出库口坐标
    private String outNo;
    //异常类型
    private String exceptionType;
    //异常坐标
    private String errorLocal;

    /**
     * 构造方法
     */
    //上传任务状态
    public SendMessageToWms(TaskDB taskDB, MsgCodeWithWms msgCodeWithWms, TaskDB.TaskProgress taskProgress, String outNo) {
        this.taskDB = taskDB;
        this.msgCodeWithWms = msgCodeWithWms;
        this.taskProgress = taskProgress;
        this.outNo = outNo;
    }
    //上传异常信息
    public SendMessageToWms(TaskDB taskDB, MsgCodeWithWms msgCodeWithWms,String exceptionType ,String errorLocal ) {
        this.taskDB = taskDB;
        this.msgCodeWithWms = msgCodeWithWms;
        this.exceptionType = exceptionType;
        this.errorLocal = errorLocal;
    }

    @Override
    public void run() {
        synchronized (this) {
            //wms下发的任务才向wms上报相关信息
            if (taskDB.getWmsTaskId() != null) {
                switch (msgCodeWithWms) {
                    //上传任务状态
                    case TaskStatus:
                        sendTaskStatusToWms();
                        break;
                    //请求入库
                    case AskForInStock:
                        sendAskForInStockToWms();
                        break;
                    //上传小车信息
                    case RgvInfo:
                        sendRgvInfoToWms();
                        break;
                    //上传输送线状态
                    case LineStatus:
                        sendLineStatusToWms();
                        break;
                    //上传异常信息
                    case ExceptionMsg:
                        sendExceptionMsgToWms();
                        break;
                    default:
                        LogUtil.info("MsgCodeWithWms类型错误");
                }
            } else {
                LogUtil.info("此任务(id=" + taskDB.getId() + ")，为非WMS下发的任务，不用上报相关信息给wms。");
            }
        }
    }

    private void sendTaskStatusToWms() {
        SendTaskStatusModel sendTaskStatusModel = new SendTaskStatusModel();
        sendTaskStatusModel.setMsgCode(msgCodeWithWms + "");
        sendTaskStatusModel.setMsgTime(String.valueOf(System.currentTimeMillis()));
        sendTaskStatusModel.setWmsTaskId(taskDB.getWmsTaskId());
        sendTaskStatusModel.setOutNo(outNo);
        sendTaskStatusModel.setTaskStatus(taskProgress.getProgressNum() + "");
        sendTaskStatusModel.send(sendTaskStatusModel);
    }

    private void sendAskForInStockToWms(){

    }
    private void sendRgvInfoToWms(){

    }
    private void sendLineStatusToWms(){

    }
    private void sendExceptionMsgToWms(){
        SendExceptionInfoModel sendExceptionInfoMode = new SendExceptionInfoModel();
        sendExceptionInfoMode.setMsgCode(msgCodeWithWms + "");
        sendExceptionInfoMode.setErrorMsg(exceptionType);
        sendExceptionInfoMode.setErrorLocal(errorLocal);
        sendExceptionInfoMode.send(sendExceptionInfoMode);
    }

}
