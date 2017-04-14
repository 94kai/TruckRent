package com.truck.Servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.Base64.Decoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.truck.Dao.UserDao;
import com.truck.Util.Base64Util;
import com.truck.Dao.IMEIDao;
import com.truck.model.User;

import net.sf.json.JSONObject;

public class UploadHeadServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String imgName=null ;

		String realPath = request.getRealPath("");// 项目绝对路径
		String phoneNumber = (String) request.getParameter("userphonenumber");
		String imgcode = (String) request.getParameter("imgcode");
		if (imgcode == null) {
			// 图片为空
			imgName="";
		} else {
			byte[] decode = Base64Util.decode(imgcode);
			// Base64解码
			for (int i = 0; i < decode.length; ++i) {
				if (decode[i] < 0) {// 调整异常数据
					decode[i] += 256;
				}
			}
			// 生成jpeg图片  + 
			String imgFilePath = realPath + "imghead/";// 新生成的图片
			File file = new File(imgFilePath);
			if(!file.exists()){
				file.mkdirs();
			}
			File file2 = new File(file,phoneNumber + ".png" );
			if(!file2.exists()){
				file2.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(file2);
			out.write(decode);
			out.flush();
			out.close();
			imgName=phoneNumber + ".png";
		}
		// 解析一下imgcode 把他保存到本地 用用户名来命名
		// 返回url
		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.print(imgName);
		outputStream.flush();
		outputStream.close();
	}

}
