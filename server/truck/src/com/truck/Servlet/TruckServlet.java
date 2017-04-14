package com.truck.Servlet;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.truck.Dao.TruckDao;
import com.truck.model.Truck;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TruckServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		   resp.setHeader("Content-type", "text/html;charset=UTF-8");  
		JSONArray jsonArray = new JSONArray();
		JSONObject json=new  JSONObject();
		TruckDao truckDao=new TruckDao();
		String action = req.getParameter("action");
		if(action.equals("add")){
			//添加一个货车信息
			Truck truck = new Truck();
			truck.setHeight(req.getParameter("height"));
			truck.setWeight(req.getParameter("weight"));
			truck.setWidth(req.getParameter("width"));
			truck.setTruckbirth(req.getParameter("truckbirth"));
			truck.setVariety(req.getParameter("variety"));
			truck.setLength(req.getParameter("length"));
			truck.setTruckcardnumber(req.getParameter("truckcardnumber"));
			int addTruck = truckDao.addTruck(truck);
			if(addTruck>=0){
				//添加到车辆拥有表中
				boolean addTruckUserRelation = truckDao.addTruckUserRelation(req.getParameter("userId"), addTruck+"");
				if(addTruckUserRelation){
					json.put("addtruckstate", true);
					//返回成功信息
				}else{
					//返回失败的信息
					json.put("addtruckstate", false);
				}
			}else{
				//返回添加失败的json
				json.put("addtruckstate", false);
			}
		}else if(action.equals("delete")){
			//删除一个货车信息
		}else if(action.equals("queryAllTrucks")){
			//获取当前用户的所有车辆
			String userid = req.getParameter("userid");
			List<Truck> trucks = truckDao.queryAllTruckByUserid(userid);
			for(Truck truck:trucks){
				JSONObject jsonTruck = JSONObject.fromObject(truck);
				jsonArray.add(jsonTruck);
				
			}
			ServletOutputStream outputStream = resp.getOutputStream();
			outputStream.write(jsonArray.toString().getBytes("utf-8"));
			outputStream.flush();
			outputStream.close();
			return;
		}
		ServletOutputStream outputStream = resp.getOutputStream();
		outputStream.print(json.toString());
		outputStream.flush();
		outputStream.close();
	}

}
