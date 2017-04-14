package com.truck.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import com.truck.Dao.UserDao;

public class RigisterServlet extends HttpServlet {
	// private UserDao userDao;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDao ud = new UserDao();
		JSONObject json = new JSONObject();

		String phoneNumber = (String) request.getParameter("phoneNumber");
		String password = (String) request.getParameter("password");
		int insertCode = ud.addUser(phoneNumber, password);

		if (insertCode < 0) {
			json.put("registerStateCode", 0);
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.print(json.toString());
			outputStream.flush();
			outputStream.close();
			return;
		} else {
			// 否则调用logionservlet 并且把json放到request中 请求loginservlet
			json.put("registerStateCode", 1);

			request.setAttribute("json", json);
			request.getRequestDispatcher("LoginServlet").forward(request, response);
		}
	}
}
