package com.truck.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.truck.Util.JDBCUtil;
import com.truck.model.Truck;
import com.truck.model.User;

public class TruckDao {
	public int addTruck(Truck truck) {
		ResultSet rs = null;
		int executeUpdate = -1;
		PreparedStatement pstm = null;
		Connection con = JDBCUtil.getCon();
		String sql = "insert  into tb_truck(truckbirth,length,width,height,weight,variety,truckcardnumber) values(?,?,?,?,?,?,?)";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, truck.getTruckbirth());
			pstm.setString(2, truck.getLength());
			pstm.setString(3, truck.getWidth());
			pstm.setString(4, truck.getHeight());
			pstm.setString(5, truck.getWeight());
			pstm.setString(6, truck.getVariety());
			pstm.setString(7, truck.getTruckcardnumber());
			executeUpdate = pstm.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		} finally {
			JDBCUtil.close(con, pstm, rs);
		}
		return queryLastTruckId();
	}

	/**
	 * 查询当前数据库中最后一条数据的id 也就是刚添加的
	 * 
	 * @return
	 */
	private int queryLastTruckId() {
		int id = -1;
		ResultSet rs = null;
		PreparedStatement pstm = null;
		Connection con = JDBCUtil.getCon();
		User user = null;
		String sql = "select * from tb_truck  ";
		try {
			pstm = con.prepareStatement(sql);
			rs = pstm.executeQuery();
			while (rs.next()) {
			} // 循环到最后一条
			rs.last();
			id = rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.close(con, pstm, rs);
		}
		return id;
	}

	public boolean addTruckUserRelation(String userId,String truckId){
		ResultSet rs = null;
		int executeUpdate = -1;
		PreparedStatement pstm = null;
		Connection con = JDBCUtil.getCon();
		String sql = "insert  into tb_user_truck(userid,turckid) values(?,?)";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1,userId);
			pstm.setString(2, truckId);
			executeUpdate = pstm.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil.close(con, pstm, rs);
		}
		return executeUpdate>0?true:false;
	}
	
	
	public List<Truck> queryAllTruckByUserid(String userid){
		ResultSet rs = null;
		ArrayList<Truck> trucks = new ArrayList<Truck>();
		PreparedStatement pstm = null;
		Connection con = JDBCUtil.getCon();
		String sql = "select * from tb_user_truck where userid=?";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1,userid);
			rs = pstm.executeQuery();
			while(rs.next()){
				String truckid = rs.getString(2);
				Truck truck = queryTruckByTruckid(truckid);
				trucks.add(truck);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.close(con, pstm, rs);
		}
		return trucks;
	}
	public Truck queryTruckByTruckid(String truckid){
		ResultSet rs = null;
		Truck truck=null;
		PreparedStatement pstm = null;
		Connection con = JDBCUtil.getCon();
		String sql = "select * from tb_truck where _id=?";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1,truckid);
			rs = pstm.executeQuery();
			while(rs.next()){
				truck = new Truck();
				truck.setId(rs.getString(1));
				truck.setTruckbirth(rs.getString(2));
				truck.setLength(rs.getString(3));
				truck.setWidth(rs.getString(4));
				truck.setHeight(rs.getString(5));
				truck.setWeight(rs.getString(6));
				truck.setVariety(rs.getString(7));
				truck.setTruckcardnumber(rs.getString(8));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.close(con, pstm, rs);
		}
		return truck;
	}
}
