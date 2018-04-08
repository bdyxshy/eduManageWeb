package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.BanJi;
import entity.Student;

public class StudentDao extends BaseDao {

	public List<Student> searchAll() {
		List<Student> list = new ArrayList<Student>();
		try {
			// 生成SQL执行器
			getStatement();
			// 5 执行sql语句
			rs = stat
					.executeQuery("select s.*,bj.name as bjName,bj.stuNums  from student as s left join banji as bj on s.bj_id=bj.id ");
			// 6 处理结果
			while (rs.next()) {
				Student stu = new Student();
				stu.setId(rs.getInt("id"));
				stu.setName(rs.getString("name"));
				stu.setSex(rs.getString("sex"));
				stu.setAge(rs.getInt("age"));

				BanJi bj = new BanJi();
				bj.setId(rs.getInt("bj_id"));
				bj.setName(rs.getString("bjName"));
				bj.setStuNums(rs.getInt("stuNums"));
				stu.setBj(bj);

				list.add(stu);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 7 关闭连接
			closeAll();
		}
		return list;
	}

	public Student searchById(int id) {
		Student stu = new Student();
		try {
			// 生成SQL执行器
			getStatement();
			// 5 执行sql语句

			rs = stat
					.executeQuery("select s.*,bj.name as bjName,bj.stuNums  from student as s left join banji as bj on s.bj_id=bj.id where s.id="
							+ id);
			// 6 处理结果
			while (rs.next()) {
				stu.setId(rs.getInt("id"));
				stu.setName(rs.getString("name"));
				stu.setSex(rs.getString("sex"));
				stu.setAge(rs.getInt("age"));
				BanJi bj = new BanJi();
				bj.setId(rs.getInt("bj_id"));
				bj.setName(rs.getString("bjName"));
				bj.setStuNums(rs.getInt("stuNums"));
				stu.setBj(bj);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 7 关闭连接
			closeAll();
		}
		return stu;
	}

	public boolean add(Student stu) {
		boolean flag = false;
		try {

			// 生成SQL执行器
			getStatement();
			// 5 执行sql语句
			String sql = "insert into student (name,sex,age,bj_id) values('"
					+ stu.getName() + "','" + stu.getSex() + "',"
					+ stu.getAge() + "," + stu.getBj().getId() + ")";
			int result = stat.executeUpdate(sql);
			// 6 处理结果

			if (result > 0) {
				flag = true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 7 关闭连接
			closeAll();
		}
		return flag;
	}

	public boolean update(Student stu) {
		boolean flag = false;
		try {

			// 4 建立sql执行器
			String sql = "update student set name=?,sex=?,age=?,bj_id=? where id=?";
			getPreparedStatement(sql);
			pstat.setString(1, stu.getName());
			pstat.setString(2, stu.getSex());
			pstat.setInt(3, stu.getAge());
			pstat.setInt(4, stu.getBj().getId());

			pstat.setInt(5, stu.getId());
			// 5 执行sql语句
			int result = pstat.executeUpdate();
			// 6 处理结果

			if (result > 0) {
				flag = true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 7 关闭连接
			closeAll();
		}
		return flag;
	}

	public boolean delete(int id) {

		boolean flag = false;
		getStatement();

		int result;
		try {
			result = stat.executeUpdate("delete from student where id=" + id);

			if (result > 0) {
				flag = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll();
		}

		return flag;
	}

	public int getIdByName(String name)
	{
		int sid=-1;
		getStatement();
		String sql="select id from student where name='"+name+"'";
		try {
			rs=stat.executeQuery(sql);
			while (rs.next()) {
				sid=rs.getInt("id");
				
		}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			closeAll();
		}
		return sid;
		
	}
	
	public List<Student> searchByCondition(Student condition) {
		List<Student> list = new ArrayList<Student>();

		getStatement();
		String where = "";
		String str = " where ";
		if (!condition.getName().equals("")) {

			where += str + "  s.name like '%" + condition.getName() + "%'";
			str = " and ";
		}
		if (!condition.getSex().equals("")) {
			where += str + "  sex='" + condition.getSex() + "'";
			str = " and ";
		}
		if (condition.getAge() != -1) {
			where += str + "   age=" + condition.getAge();
			str = " and ";
		}
		if (condition.getBj() != null && condition.getBj().getId() == -1) {
			where += str + "   bj_id=0 or bj_id is null";

		} else if (condition.getBj() != null && condition.getBj().getId() != 0) {
			where += str + "   bj_id=" + condition.getBj().getId();
		}

		String sql = "select s.*,bj.name as bjName,bj.stuNums  from student as s left join banji as bj on s.bj_id=bj.id "
				+ where;

		System.out.println(sql);
		try {
			rs = stat.executeQuery(sql);
			// 6 处理结果
			while (rs.next()) {
				Student stu = new Student();
				stu.setId(rs.getInt("id"));
				stu.setName(rs.getString("name"));
				stu.setSex(rs.getString("sex"));
				stu.setAge(rs.getInt("age"));

				BanJi bj = new BanJi();
				bj.setId(rs.getInt("bj_id"));
				bj.setName(rs.getString("bjName"));
				bj.setStuNums(rs.getInt("stuNums"));
				stu.setBj(bj);
				list.add(stu);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll();
		}

		return list;
	}
}
