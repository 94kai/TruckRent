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
import com.truck.Dao.TruckSourceDao;
import com.truck.model.Truck;
import com.truck.model.TruckSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
public class TruckSourceServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JSONArray jsonArray = new JSONArray();
		JSONObject json = new JSONObject();
		TruckSourceDao truckSourceDao = new TruckSourceDao();
		String action = req.getParameter("action");
		if (action.equals("add")) {
			// 添加一个货车车源信息
			TruckSource truckSource = new TruckSource();
			truckSource.setLoad_date(req.getParameter("load_date"));
			truckSource.setStart_place(req.getParameter("start_place"));
			truckSource.setStop_place(req.getParameter("stop_place"));
			truckSource.setStart_place_point(req.getParameter("start_place_point"));
			truckSource.setIntrocduce(req.getParameter("introcduce"));
			truckSource.setState(req.getParameter("state"));
			truckSource.setTruck_id(req.getParameter("truck_id"));

			int addTruckSource = truckSourceDao.addTruckSource(truckSource);
			if (addTruckSource >= 0) {
				// 添加到车源拥有表中
				boolean addTruckSourceUserRelation = truckSourceDao.addTruckSourceUserRelation(req.getParameter("userid"), addTruckSource+"");
				if (addTruckSourceUserRelation) {
					json.put("addtrucksourcestate", true);
					// 返回成功信息
				} else {
					// 返回失败的信息
					json.put("addtrucksourcestate", false);
				}
			} else {
				// 返回添加失败的json
				json.put("addtrucksourcestate", false);
			}
		} else if (action.equals("delete")) {
			// 删除一个车源信息
		} else if (action.equals("queryMyAllTruckSources")) {
			// 获取当前用户的所有车源
			String userid = req.getParameter("userid");
			List<TruckSource> truckSources = truckSourceDao.queryAllTruckSourceByUserid(userid);
			for (TruckSource truckSource : truckSources) {
				JSONObject jsonTruckSource = JSONObject.fromObject(truckSource);
				jsonArray.add(jsonTruckSource);
			}
			ServletOutputStream outputStream = resp.getOutputStream();
			outputStream.write(jsonArray.toString().getBytes("utf-8"));
			outputStream.flush();
			outputStream.close();
			return;
		} else if(action.equals("queryAllTruckSources")){
			//查询所有的车源信息
			//获取条件  客户端如果没有限定条件 传入通配符 %
			String startplace = req.getParameter("startplace");
			String stopplace = 	req.getParameter("stopplace");
			String starttime = 	req.getParameter("starttime");
			List<TruckSource> truckSources =  truckSourceDao.queryAllTruckSourceByCondition(startplace,stopplace,starttime);
			for (TruckSource truckSource : truckSources) {
				JSONObject jsonTruckSource = JSONObject.fromObject(truckSource);
				jsonArray.add(jsonTruckSource);
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
