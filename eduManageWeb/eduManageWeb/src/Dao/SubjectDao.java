package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Entity.Subject;

public class SubjectDao extends BaseDao{
	public List<Subject> searchNoSubByBjId(int bjId) {
		List<Subject> list = new ArrayList<Subject>();
		try {
			getStatement();
			String sql = "select * from subject where id not in(select subId from v_bj_sub where bjId="+bjId+" and subId is not null)";
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				Subject sub=new Subject();
				sub.setId(rs.getInt("id"));
				sub.setName(rs.getString("name"));
				list.add(sub);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return list;
	}
	public List<Subject> searchAll() {
		List<Subject> list = new ArrayList<Subject>();
		try {
			getStatement();
			String sql = "select * from subject";
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				Subject sub=new Subject();
				sub.setId(rs.getInt("id"));
				sub.setName(rs.getString("name"));
				list.add(sub);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return list;
	}
	public List<Subject> searchByBegin(int begin,int num) {
		List<Subject> list = new ArrayList<Subject>();
		try {
			getStatement();
			String sql = "select * from subject limit "+begin+","+num;
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				Subject sub=new Subject();
				sub.setId(rs.getInt("id"));
				sub.setName(rs.getString("name"));
				list.add(sub);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return list;
	}
	public int searchCount() {
		int count=0;
		try {
			getStatement();
			String sql = "select count(id) from subject";
			rs=stat.executeQuery(sql);
			if(rs.next()){
				count=rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return count;
	}
	public int searchCount(Subject sub) {
		int count=0;
		try {
			getStatement();
			String where=" where 1=1 ";
			if(!"".equals(sub.getName())){
				where+=" and name='"+sub.getName()+"'";
			}
			String sql = "select count(id) from subject"+where;
			rs=stat.executeQuery(sql);
			if(rs.next()){
				count=rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return count;
	}
	public boolean  add(Subject sub) {
		boolean flag=false;
		try {
			String sql="insert into subject (name) values (?)";
			getPreParedStatement(sql);
			pstat.setString(1,sub.getName());
			int result=pstat.executeUpdate();
			if(result>0){
				flag=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return flag;			
	}

	public Subject searchById(int id) {
		Subject sub=null;
		try {
			getStatement();
			String sql = "select * from subject where id="+id;
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				sub=new Subject();
				sub.setId(rs.getInt("id"));
				sub.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return sub;
	}
	public boolean modify(Subject sub) {
		boolean flag=false;
		try {
			String sql = "update subject set name=? where id=?";
			getPreParedStatement(sql);
			pstat.setString(1, sub.getName());
			pstat.setInt(2, sub.getId());
			int result=pstat.executeUpdate();
			if(result>0) {
				flag=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return flag;
	}

	public boolean delete(int id) {
		boolean flag=false;
		try {
			String sql = "delete from subject where id=?";
			getPreParedStatement(sql);
			pstat.setInt(1, id);
			int result=pstat.executeUpdate();
			if(result>0) {
				flag=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return flag;
	}

	public List<Subject> searchByCondition(Subject sub,int begin,int num) {
		List<Subject> list=new ArrayList<Subject>();
		String where=" where 1=1 ";
		if(!"".equals(sub.getName())){
			where+=" and name='"+sub.getName()+"'";
		}
		try {
			getStatement();
			String sql = "select * from subject "+where+" limit "+begin+","+num;
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				Subject sub1=new Subject();
				sub1.setId(rs.getInt("id"));
				sub1.setName(rs.getString("name"));
				list.add(sub1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public List<Subject> searchByBjId(int bjId) {
		List<Subject> list = new ArrayList<Subject>();
		try {
			getStatement();
			String sql="";
			if(bjId==-1){
				sql = "select sub.id as subId,sub.name as subName from subject as sub ";
			}else{
				sql = "select sub.id as subId,sub.name as subName from subject as sub "
						+"inner join sub_bj as m on m.subId=sub.id "
						+"inner join banji as bj on bj.id=m.bjId "
						+"where bjId="+bjId;
			}
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				Subject sub=new Subject();
				sub.setId(rs.getInt("subId"));
				sub.setName(rs.getString("subName"));
				list.add(sub);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return list;
	}
}
