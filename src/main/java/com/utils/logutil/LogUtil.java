package com.utils.logutil;

import com.utils.properties.ConfigFileInit;
import org.apache.log4j.Logger;

/**
 * @author xs
 * @date 2019/08/26 14:42
 * log4j工具类
 */
public class LogUtil {
    /**
     * 加载日志输出的路径，该路径配置在configs.properties文件中
     */
    static void initLogFilePath() {
        ConfigFileInit.getProperties();
    }
    private static final Logger thread = Logger.getLogger("thread");
    private static final Logger timer = Logger.getLogger("timer");
    private static final Logger socket = Logger.getLogger("socket");

    public synchronized static void threadInfo(String msg) {
        thread.info(msg);
    }

    public synchronized static void threadError(String msg) {
        thread.error(msg);
    }

    public synchronized static void threadDebug(String msg) {
        thread.debug(msg);
    }

    public synchronized static void threadWarn(String msg) {
        thread.warn(msg);
    }

    public synchronized static void timerInfo(String msg) {
        timer.info(msg);
    }

    public synchronized static void timerError(String msg) {
        timer.error(msg);
    }

    public synchronized static void timerDebug(String msg) {
        timer.debug(msg);
    }

    public synchronized static void timerWarn(String msg) {
        timer.warn(msg);
    }

    public synchronized static void socketInfo(String msg) {
        socket.info(msg);
    }

    public synchronized static void socketError(String msg) {
        socket.error(msg);
    }

    public synchronized static void socketDebug(String msg) {
        socket.debug(msg);
    }

    public synchronized static void socketWarn(String msg) {
        socket.warn(msg);
    }

}
