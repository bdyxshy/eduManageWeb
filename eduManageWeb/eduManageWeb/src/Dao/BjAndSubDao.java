package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Entity.BanJi;
import Entity.Subject;

public class BjAndSubDao extends BaseDao{
	public boolean  addAll(int bjId,String []subIds) {
		boolean flag=true;
		try {
			Connection conn=getConnection();
			conn.setAutoCommit(false);
			for(int i=0;i<subIds.length;i++){
				boolean f=add(conn,bjId, Integer.parseInt(subIds[i]));
				if(f==false){
					flag=false;
					break;
				}
			}
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return flag;
	}
	public boolean  add(Connection conn,int bjId,int subId) {
		boolean flag=false;
		try {
			String sql="insert into sub_bj (bjId,subId) values (?,?)";
			PreparedStatement pstat=conn.prepareStatement(sql);
			pstat.setInt(1,bjId);
			pstat.setInt(2,subId);
			int result=pstat.executeUpdate();
			if(result>0){
				flag=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;			
	}
	public boolean  deleteAll(int bjId,String []subIds) {
		boolean flag=true;
		try {
			Connection conn=getConnection();
			conn.setAutoCommit(false);
			for(int i=0;i<subIds.length;i++){
				boolean f=delete(conn,bjId, Integer.parseInt(subIds[i]));
				if(f==false){
					flag=false;
					break;
				}
			}
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return flag;
	}
	public boolean  delete(Connection conn, int bjId,int subId) {
		boolean flag=false;
		try {
			String sql="delete from sub_bj where bjId=? and subId=?";
			PreparedStatement pstat=conn.prepareStatement(sql);
			pstat.setInt(1,bjId);
			pstat.setInt(2,subId);
			int result=pstat.executeUpdate();
			if(result>0){
				flag=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;			
	}
}
