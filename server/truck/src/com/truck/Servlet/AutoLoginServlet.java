package com.truck.Servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.truck.Dao.UserDao;
import com.truck.Dao.IMEIDao;
import com.truck.model.User;

import net.sf.json.JSONObject;

public class AutoLoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IMEIDao objIMEIDao = new IMEIDao();
		String phoneNumber = (String) request.getParameter("phoneNumber");
		String imei = (String) request.getParameter("IMEI");
		System.out.println(imei);
		JSONObject json;
		json = (JSONObject) request.getAttribute("json");
		if (json == null) {
			// 說明客戶端進行自动登陸
			json = new JSONObject();
			// 查数据 然后返回json
			boolean query = objIMEIDao.query(phoneNumber, imei);
			json.put("isLogin", query);
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.print(json.toString());
			outputStream.flush();
			outputStream.close();
		} else {
			// 说明客户端是通过登陆进入的这个servlet 所以进行插入数据
			boolean updateIMEI = objIMEIDao.updateIMEI(phoneNumber, imei);
			json.put("addIMEIStateCode", updateIMEI?1:0);
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.print(json.toString());
			outputStream.flush();
			outputStream.close();
			
		}
	}
}
