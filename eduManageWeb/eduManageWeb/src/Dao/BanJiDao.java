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

public class BanJiDao extends BaseDao{
	public List<BanJi> searchAll() {
		List<BanJi> list = new ArrayList<BanJi>();
		try {
			getStatement();
			String sql = "select * from banji";
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				BanJi bj=new BanJi();
				bj.setId(rs.getInt("id"));
				bj.setName(rs.getString("name"));
				bj.setNums(rs.getInt("nums"));
				list.add(bj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return list;
	}
	public List<BanJi> searchByBegin(int begin,int num) {
		List<BanJi> list = new ArrayList<BanJi>();
		try {
			getStatement();
			String sql = "select * from banji limit "+begin+","+num;
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				BanJi bj=new BanJi();
				bj.setId(rs.getInt("id"));
				bj.setName(rs.getString("name"));
				bj.setNums(rs.getInt("nums"));
				list.add(bj);
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
			String sql = "select count(id) from banji";
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
	public int searchCount(BanJi bj) {
		int count=0;
		try {
			getStatement();
			String where=" where 1=1 ";
			if(!"".equals(bj.getName())){
				where+=" and name='"+bj.getName()+"'";
			}
			String sql = "select count(id) from banji"+where;
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
	public boolean  add(BanJi bj) {
		boolean flag=false;
		try {
			String sql="insert into banji (name) values (?)";
			getPreParedStatement(sql);
			pstat.setString(1,bj.getName());
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
	public BanJi searchBjAndSubById(int id) {
		BanJi bj=new BanJi();
		List<Subject>list=new ArrayList<Subject>();
		try {
			getStatement();
			String sql = "select * from v_bj_sub where bjId="+id;
			rs = stat.executeQuery(sql);
			int i=0;
			while (rs.next()) {
				if(i==0){
					bj.setId(rs.getInt("bjId"));
					bj.setName(rs.getString("bjName"));
					bj.setNums(rs.getInt("nums"));
				}
				Subject sub=new Subject();
				sub.setId(rs.getInt("subId"));
				sub.setName(rs.getString("subName"));
				if(sub.getId()!=0){
					list.add(sub);
				}
				i++;
			}
			bj.setSubs(list);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return bj;
	}
	public BanJi searchById(int id) {
		BanJi bj=null;
		try {
			getStatement();
			String sql = "select * from banji where id="+id;
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				bj=new BanJi();
				bj.setId(rs.getInt("id"));
				bj.setName(rs.getString("name"));
				bj.setNums(rs.getInt("nums"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return bj;
	}
	public boolean modify(BanJi bj) {
		boolean flag=false;
		try {
			String sql = "update banji set name=? where id=?";
			getPreParedStatement(sql);
			pstat.setString(1, bj.getName());
			pstat.setInt(2, bj.getId());
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
			String sql = "delete from banji where id=?";
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

	public List<BanJi> searchByCondition(BanJi bj,int begin,int num) {
		List<BanJi> list=new ArrayList<BanJi>();
		String where=" where 1=1 ";
		if(!"".equals(bj.getName())){
			where+=" and name='"+bj.getName()+"'";
		}
		try {
			getStatement();
			String sql = "select * from banji "+where+" limit "+begin+","+num;
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				BanJi bj1=new BanJi();
				bj1.setId(rs.getInt("id"));
				bj1.setName(rs.getString("name"));
				bj1.setNums(rs.getInt("nums"));
				list.add(bj1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
