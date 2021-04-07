package com.utils.webapi.sendToWms;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.suray.enums.SysParam;
import com.suray.route.util.LogUtil;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

public class SendToWmsCore {
	private static String url = SysParam.SendToWmsUrl;
	@JsonProperty("MsgCode")
	public String MsgCode;
	@JsonProperty("MsgTime")
	public String MsgTime;

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

	private static boolean sendPost(String msg,String title) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url+ "/" + title);
			URLConnection conn = realUrl.openConnection();// 打开和URL之间的连接
			conn.setRequestProperty("Accept", "application/json");// 设置通用的请求属性
			conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			conn.setRequestProperty("User-Agent", "Jakarta Commons-HttpClient/3.1");
			conn.setDoOutput(true);// 发送POST请求必须设置如下两行
			conn.setDoInput(true);
			out = new PrintWriter(conn.getOutputStream());//获取URLConnection对象对应的输出流
			//out=new OutputStreamWriter(conn.getOutputStream(),"UTF-8")//中文有乱码的需要将PrintWriter改为如下
			out.print(msg);  // 发送请求参数
			out.flush();
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));// 定义BufferedReader输入流来读取URL的响应
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！"+e);
			e.printStackTrace();
		}
		finally{
			try{
				if(out!=null){
					out.close();
				}
				if(in!=null){
					in.close();
				}
			}
			catch(IOException ex){
				ex.printStackTrace();
			}
		}
		System.out.println("返回结果为："+result);

		if (result!=null&&result.length()>0) {
			return AcceptFromWmsCore.analysisJson(result);
		}else {
			return false;
		}
	}
	public  boolean send(SendToWmsCore sendToWms) {
		boolean result =false;
		try {
			String msg = new ObjectMapper().writeValueAsString(sendToWms);
			LogUtil.info("向WMS发送消息："+msg);
			result = sendPost(msg, sendToWms.getMsgCode());
			int i=0;
			while(!result){
				result =sendPost(msg, sendToWms.getMsgCode());
				i++;
				if (i>5)break;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	//	//发送
	//	public  Boolean sendPost(SendToWmsCore sendToWms) {
	//		PrintWriter out = null;
	//		BufferedReader in = null;
	//		String result = "";
	//		try {
	//			//			String  urlStr = "http://192.168.165.10:8088/api/Fwcs/AskForInStock";
	//			URL realUrl = new URL(url + "/" + sendToWms.getMsgCode());
	//			URLConnection conn = realUrl.openConnection();// 打开和URL之间的连接
	//			conn.setRequestProperty("Accept", "application/json");// 设置通用的请求属性
	//			conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
	//			conn.setRequestProperty("User-Agent", "Jakarta Commons-HttpClient/3.1");
	//			conn.setDoOutput(true);// 发送POST请求必须设置如下两行
	//			conn.setDoInput(true);
	//			out = new PrintWriter(conn.getOutputStream());//获取URLConnection对象对应的输出流
	//			//out=new OutputStreamWriter(conn.getOutputStream(),"UTF-8")//中文有乱码的需要将PrintWriter改为如下
	//
	//			String sendString = new ObjectMapper().writeValueAsString(sendToWms);//获取需要发送的字符串
	//			out.print(sendString);    // 发送请求参数
	//
	//			out.flush();
	//
	//			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));// 定义BufferedReader输入流来读取URL的响应
	//			String line;
	//			while ((line = in.readLine()) != null) {
	//				result += line;
	//			}
	//		} catch (Exception e) {
	//			System.out.println("发送 POST 请求出现异常！" + e);
	//			e.printStackTrace();
	//			return false;
	//		} finally {
	//			try {
	//				if (out != null) {
	//					out.close();
	//				}
	//				if (in != null) {
	//					in.close();
	//				}
	//			} catch (IOException e) {
	//				e.printStackTrace();
	//			}
	//		}
	//		System.out.println("返回结果为：" + result);
	//		if (result!=null&&result.length()>0) {
	//			//	        处理返回结果
	//			AcceptFromWmsCore acceptFromWmsCore = new AcceptFromWmsCore();
	//			return acceptFromWmsCore.analysisJson(result);
	//		}else {
	//			return false;
	//		}
	//	}

	/**
	 * 获取json消息类型
	 *
	 * @param json
	 * @return
	 */
	public static String getMsgCode(String json) {
		JSONObject jsonObject = JSONObject.fromObject(json);
		String msgCode = null;
		try {
		 msgCode = jsonObject.getString("MsgCode");
		}catch(net.sf.json.JSONException e) {
			 msgCode = jsonObject.getString("msgCode");
		}
		return msgCode;
	}
}
