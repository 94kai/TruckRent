package com.truck.Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.truck.Dao.UserDao;
import com.truck.model.User;

public class LoginServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserDao ud = new UserDao();
		String phoneNumber = (String) req.getParameter("phoneNumber");
		String password = (String) req.getParameter("password");
		JSONObject json;
		json = (JSONObject) req.getAttribute("json");
		if (json == null) {
			// 說明客戶端直接要進行登陸
			json = new JSONObject();
		}

		User user = (User) ud.query(phoneNumber, password);
		if (user != null) {
			// TODO 登陆成功后 需要在leanCloud注册一个账号 并且存入数据库中 同时put给json
			// 同时登陆leanCloud服务器
			// 说明登陆成功
			json.put("loginStateCode", 1);
			json.put("leanCloudId", user.getLeanCloudId() + "");

			// 跳转到ImeiServlet中 设置自动登录
			// 调用ImeiServlet 并且把json放到request中 请求loginservlet
			req.setAttribute("json", json);
			req.getRequestDispatcher("AutoLoginServlet").forward(req, resp);

		} else {
			json.put("loginStateCode", 0);
			ServletOutputStream outputStream = resp.getOutputStream();
			outputStream.print(json.toString());
			outputStream.flush();
			outputStream.close();
			return;
		}

	}

}