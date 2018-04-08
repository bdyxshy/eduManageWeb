package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.BanJi;

public class BanJiDao extends BaseDao {

	public List<BanJi> searchAll() {
		List<BanJi> list = new ArrayList<BanJi>();
		try {
			// 生成SQL执行器
			getStatement();
			// 5 执行sql语句
			rs = stat.executeQuery("select * from banji");
			// 6 处理结果
			while (rs.next()) {
				BanJi bj = new BanJi();
				bj.setId(rs.getInt("id"));
				bj.setName(rs.getString("name"));
				bj.setStuNums(rs.getInt("stuNums"));
				list.add(bj);
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

	public BanJi searchById(int id) {
		BanJi bj = new BanJi();
		try {
			// 生成SQL执行器
			getStatement();
			// 5 执行sql语句

			rs = stat.executeQuery("select * from banji where id=" + id);
			// 6 处理结果
			while (rs.next()) {

				bj.setId(rs.getInt("id"));
				bj.setName(rs.getString("name"));
				bj.setStuNums(rs.getInt("stuNums"));

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 7 关闭连接
			closeAll();
		}
		return bj;
	}

	public boolean add(BanJi bj) {
		boolean flag = false;
		try {

			// 生成SQL执行器
			getStatement();
			// 5 执行sql语句
			String sql = "insert into banji (name) values('" + bj.getName()
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

	public boolean update(BanJi bj) {
		boolean flag = false;
		try {

			// 4 建立sql执行器
			String sql = "update banji set name=? where id=?";
			getPreparedStatement(sql);
			pstat.setString(1, bj.getName());
			pstat.setInt(2, bj.getId());
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
			result = stat.executeUpdate("delete from banji where id=" + id);

			if (result > 0) {
				flag = true;
			}
			
			result = stat.executeUpdate("update student set bj_id=0 where bj_id=" + id);
			if (result > 0) {
				flag = true;
			}else{
				flag=false;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll();
		}

		return flag;
	}

	public List<BanJi> searchByCondition(BanJi condition) {
		List<BanJi> list = new ArrayList<BanJi>();

		getStatement();
		String where = "";
		String str = " where ";
		if (!condition.getName().equals("")) {

			where += str + "  name like '%" + condition.getName() + "%'";
			str = " and ";
		}

		if (condition.getStuNums() != -1&&condition.getStuNums()!=0) {
			where += str + "   stuNums=" + condition.getStuNums();
			//where += str + "   stuNums is null ";
		}
		if (condition.getStuNums() != -1&&condition.getStuNums()==0) {
			//where += str + "   stuNums=" + condition.getStuNums();
			where += str + "   stuNums is null ";
		}
		String sql = "select * from banji  " + where;

		System.out.println(sql);
		try {
			rs = stat.executeQuery(sql);
			// 6 处理结果
			while (rs.next()) {
				BanJi bj = new BanJi();
				bj.setId(rs.getInt("id"));
				bj.setName(rs.getString("name"));
				bj.setStuNums(rs.getInt("stuNums"));
				list.add(bj);
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
