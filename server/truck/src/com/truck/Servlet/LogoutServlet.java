package com.truck.Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.truck.Dao.IMEIDao;
import com.truck.Dao.UserDao;
import com.truck.model.User;

public class LogoutServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		IMEIDao imerDao = new IMEIDao();
		String phoneNumber = (String) req.getParameter("phoneNumber");
		JSONObject json = new JSONObject();
		boolean deleteIMEI = imerDao.deleteIMEI(phoneNumber);
		if (deleteIMEI) {
			json.put("logout", 1);
		} else {
			json.put("logout", 0);
		}
		ServletOutputStream outputStream = resp.getOutputStream();
		outputStream.print(json.toString());
		outputStream.flush();
		outputStream.close();
		return;
	}

}