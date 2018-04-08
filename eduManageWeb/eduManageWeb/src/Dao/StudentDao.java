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
import Entity.Student;

public class StudentDao extends BaseDao{
	public List<Student> searchAll() {
		List<Student> list = new ArrayList<Student>();
		try {
			getStatement();
			String sql = "select s.*,bj.id as bjId,bj.name as bjName from student as s left join banji as bj on bj_id=bj.id";
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				Student stu=new Student();
				stu.setId(rs.getInt("s.id"));
				stu.setName(rs.getString("s.name"));
				stu.setSex(rs.getString("Sex"));
				stu.setAge(rs.getInt("age"));
				stu.setPhoto(rs.getString("photo"));
				BanJi bj=new BanJi();
				bj.setId(rs.getInt("bjId"));
				bj.setName(rs.getString("bjName"));
				stu.setBanji(bj);
				list.add(stu);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return list;
	}
	public List<Student> searchByBegin(int begin,int num) {
		List<Student> list = new ArrayList<Student>();
		try {
			getStatement();
			String sql = "select s.*,bj.id as bjId,bj.name as bjName from student as s left join banji as bj on bj_id=bj.id limit "+begin+","+num;
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				Student stu=new Student();
				stu.setId(rs.getInt("s.id"));
				stu.setName(rs.getString("s.name"));
				stu.setSex(rs.getString("Sex"));
				stu.setAge(rs.getInt("age"));
				stu.setPhoto(rs.getString("photo"));
				BanJi bj=new BanJi();
				bj.setId(rs.getInt("bjId"));
				bj.setName(rs.getString("bjName"));
				stu.setBanji(bj);
				list.add(stu);
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
			String sql = "select count(id) from student";
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
	public int searchCount(Student stu) {
		int count=0;
		try {
			getStatement();
			String where=" where 1=1 ";
			if(!"".equals(stu.getName())){
				where+=" and name='"+stu.getName()+"'";
			}
			if(!"".equals(stu.getSex())){
				where+=" and sex='"+stu.getSex()+"'";
			}
			if(stu.getAge()!=-1){
				where+=" and age="+stu.getAge();
			}
			if(stu.getBanji().getId()!=-1){
				where+=" and bj_id="+stu.getBanji().getId();
			}
			String sql = "select count(id) from student"+where;
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
	public boolean  add(Student stu) {
		boolean flag=false;
		try {
			String sql="insert into student (name,sex,age,bj_id,photo) values (?,?,?,?,?)";
			getPreParedStatement(sql);
			pstat.setString(1,stu.getName());
			pstat.setString(2,stu.getSex());
			pstat.setInt(3,stu.getAge());
			pstat.setInt(4,stu.getBanji().getId());
			pstat.setString(5,stu.getPhoto());
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

	public Student searchById(int id) {
		Student stu=null;
		try {
			getStatement();
			String sql = "select s.*,bj.id as bjId,bj.name as bjName from student as s left join banji as bj on bj_id=bj.id where s.id="+id;
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				stu=new Student();
				stu.setId(rs.getInt("s.id"));
				stu.setName(rs.getString("s.name"));
				stu.setSex(rs.getString("Sex"));
				stu.setAge(rs.getInt("age"));
				stu.setPhoto(rs.getString("photo"));
				BanJi bj=new BanJi();
				bj.setId(rs.getInt("bjId"));
				bj.setName(rs.getString("bjName"));
				stu.setBanji(bj);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return stu;
	}
	public boolean modify(Student stu) {
		boolean flag=false;
		try {
			String sql = "update student set name=?, sex=?, age=?,bj_id=?,photo=? where id=?";
			getPreParedStatement(sql);
			pstat.setString(1, stu.getName());
			pstat.setString(2, stu.getSex());
			pstat.setInt(3, stu.getAge());
			pstat.setInt(4, stu.getBanji().getId());
			pstat.setString(5, stu.getPhoto());
			pstat.setInt(6, stu.getId());
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
			String sql = "delete from student where id=?";
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

	public List<Student> searchByCondition(Student stu,int begin,int num) {
		List<Student> list=new ArrayList<Student>();
		String where=" where 1=1 ";
		if(!"".equals(stu.getName())){
			where+=" and s.name='"+stu.getName()+"'";
		}
		if(!"".equals(stu.getSex())){
			where+=" and sex='"+stu.getSex()+"'";
		}
		if(stu.getAge()!=-1){
			where+=" and age="+stu.getAge();
		}
		if(stu.getBanji().getId()!=-1){
			where+=" and bj_id="+stu.getBanji().getId();
		}
		try {
			getStatement();
			String sql = "select  s.*,bj.id as bjId,bj.name as bjName from student as s left join banji as bj on bj_id=bj.id  "+where+" limit "+begin+","+num;
			rs = stat.executeQuery(sql);
			while (rs.next()) {
				Student stu1=new Student();
				stu1.setId(rs.getInt("s.id"));
				stu1.setName(rs.getString("s.name"));
				stu1.setSex(rs.getString("Sex"));
				stu1.setAge(rs.getInt("age"));
				stu1.setPhoto(rs.getString("photo"));
				BanJi bj=new BanJi();
				bj.setId(rs.getInt("bjId"));
				bj.setName(rs.getString("bjName"));
				stu1.setBanji(bj);
				list.add(stu1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
