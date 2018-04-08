package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Subject;

public class SubjectDao extends BaseDao {
	public int getIdByName(String name)
	{
		int sid=-1;
		getStatement();
		String sql="select id from subject where name='"+name+"'";
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

	public List<Subject> searchAll() {
		List<Subject> list = new ArrayList<Subject>();
		try {
			// 生成SQL执行器
			getStatement();
			// 5 执行sql语句
			rs = stat.executeQuery("select * from subject");
			// 6 处理结果
			while (rs.next()) {
				Subject sub = new Subject();
				sub.setId(rs.getInt("id"));
				sub.setName(rs.getString("name"));
				list.add(sub);
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

	public Subject searchById(int id) {
		Subject sub = new Subject();
		try {
			// 生成SQL执行器
			getStatement();
			// 5 执行sql语句
			rs = stat.executeQuery("select * from subject where id=" + id);
			// 6 处理结果
			while (rs.next()) {
				sub.setId(rs.getInt("id"));
				sub.setName(rs.getString("name"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 7 关闭连接
			closeAll();
		}
		return sub;
	}

	public boolean add(Subject sub) {
		boolean flag = false;
		try {

			// 生成SQL执行器
			getStatement();
			// 5 执行sql语句
			String sql = "insert into subject (name) values('" + sub.getName()
					+ "')";
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

	public boolean update(Subject sub) {
		boolean flag = false;
		try {

			// 4 建立sql执行器
			String sql = "update subject set name=? where id=?";
			getPreparedStatement(sql);
			pstat.setString(1, sub.getName());
			pstat.setInt(2, sub.getId());
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
			result = stat.executeUpdate("delete from subject where id=" + id);

			if (result > 0) {
				flag = true;
			}
//			
//			result = stat.executeUpdate("update student set sub_id=0 where sub_id=" + id);
//			if (result > 0) {
//				flag = true;
//			}else{
//				flag=false;
//			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll();
		}

		return flag;
	}

	public List<Subject> searchByCondition(Subject condition) {
		List<Subject> list = new ArrayList<Subject>();

		getStatement();
		String where = "";
		String str = " where ";
		if (condition.getId()!=-1) {
			where += str + " id=" + condition.getId() ;
			str = " and ";
		}
		if (!condition.getName().equals("")) {
			where += str + "  name like '%" + condition.getName() + "%'";
			str = " and ";
		}
		
		String sql = "select * from subject  " + where;

		System.out.println(sql);
		try {
			rs = stat.executeQuery(sql);
			// 6 处理结果
			while (rs.next()) {
				Subject sub = new Subject();
				sub.setId(rs.getInt("id"));
				sub.setName(rs.getString("name"));
				list.add(sub);
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
