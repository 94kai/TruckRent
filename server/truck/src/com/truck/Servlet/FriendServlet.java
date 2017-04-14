package com.truck.Servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.truck.Dao.UserDao;
import com.truck.Dao.IMEIDao;
import com.truck.model.User;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FriendServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");  
		response.setCharacterEncoding("UTF-8");
		UserDao userDao = new UserDao();
		String action = (String) request.getParameter("action");
		if (action.equals("queryFriend")) {
			List<User> queryUserByPhoneNumber = userDao.queryUserByPhoneNumber(request.getParameter("queryPhone"));
			JSONArray usersJson = new JSONArray();
			for (User user : queryUserByPhoneNumber) {
				JSONObject userJson=new JSONObject();
				userJson.put("avatarurl", user.getAvatarurl());
				userJson.put("username", user.getUsername());
				userJson.put("phonenumber", user.getPhoneNumber());
				
				usersJson.add(userJson);
			}
			PrintWriter writer = response.getWriter();
			writer.write(usersJson.toString());
			writer.flush();
			writer.close();
			
			return ;
		}else if(action.equals("queryMyFriend")){
			List<String> myFriendPhones = userDao.queryMyFriend(request.getParameter("myphone"));
			JSONArray usersJson = new JSONArray();
			for (String user : myFriendPhones) {
				 usersJson.add(user);
			}
			PrintWriter writer = response.getWriter();
			writer.write(usersJson.toString());
			writer.flush();
			writer.close();
			return ;
		}else if(action.equals("caredSomeBody")){
			String myPhone = (String) request.getParameter("myPhone");
			String otherPhone = (String) request.getParameter("otherPhone");
			boolean caredAFriend = userDao.caredAFriend(myPhone, otherPhone);
			JSONObject object = new JSONObject();
			if(caredAFriend){
				object.put("result", true);
			}else{
				object.put("result", false);
			}
			
			PrintWriter writer = response.getWriter();
			writer.write(object.toString());
			writer.flush();
			writer.close();
		}else if(action.equals("cancelcaredSomeBody")){
			String myPhone = (String) request.getParameter("myPhone");
			String otherPhone = (String) request.getParameter("otherPhone");
			boolean cancelCaredAFriend = userDao.cancelCaredAFriend(myPhone, otherPhone);
			JSONObject object = new JSONObject();
			if(cancelCaredAFriend){
				object.put("result", true);
			}else{
				object.put("result", false);
			}
			
			PrintWriter writer = response.getWriter();
			writer.write(object.toString());
			writer.flush();
			writer.close();
		}else if(action.equals("querymyfriendobject")){
			List<User> users =new ArrayList<User>();

			List<String> myFriendPhones = userDao.queryMyFriend(request.getParameter("myphone"));
			for (String string : myFriendPhones) {
				User user= userDao.queryUserById(string);
				user.setIsChecked(true);
				users.add(user);
			}
			JSONArray usersJson = new JSONArray();
			for (User user : users) {
				JSONObject userJson=new JSONObject();
				userJson.put("avatarurl", user.getAvatarurl());
				userJson.put("username", user.getUsername());
				userJson.put("phonenumber", user.getPhoneNumber());
				userJson.put("ischeck", user.getIsChecked());
				usersJson.add(userJson);
			}
			PrintWriter writer = response.getWriter();
			writer.write(usersJson.toString());
			writer.flush();
			writer.close();
		}
	}
}
