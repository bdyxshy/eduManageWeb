package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.BanJi;
import entity.Subject;

public class BjSubjectDao extends BaseDao {

	public List<Subject> search(int id) {
		List<Subject> list = new ArrayList<Subject>();
		try {
			// 生成SQL执行器
			getStatement();
			// 5 执行sql语句
			rs = stat
					.executeQuery("select * from stu_cou INNER JOIN subject on stu_cou.sub_id=subject.id  where stu_cou.bj_id="
							+ id);
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

	public boolean add(String name, BanJi bj) {
		boolean flag = false;
		try {
			// 生成SQL执行器
			getStatement();
			// 5 执行sql语句
			String sql1 = "select id from subject where name='" + name + "'";
			int id = 0;
			rs = stat.executeQuery(sql1);
			while (rs.next()) {
				id = rs.getInt("id");
			}
			System.out.println(id);
			String sql = "insert into stu_cou(stu_cou.bj_id,stu_cou.sub_id) values("
					+ bj.getId() + "," + id + ")";
			System.out.println(sql);
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

	public boolean delete(int sub_id, int bj_id) {

		boolean flag = false;
		getStatement();

		int result;
		try {
			String sql = "delete from stu_cou where stu_cou.bj_id=" + bj_id
					+ " and stu_cou.sub_id=" + sub_id;
			result = stat.executeUpdate(sql);

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

	public List<String> searchUnSelectSubject(BanJi bj) {
		List<String> list = new ArrayList<>();
		try {
			// 生成SQL执行器
			getStatement();
			// 5 执行sql语句
			String sql = "select subject.name from subject where subject.id not in( select"
					+ " subject.id from stu_cou INNER JOIN subject on stu_cou.sub_id=subject.id  where stu_cou.bj_id="
					+ bj.getId() + ")";
			rs = stat.executeQuery(sql);
			// 6 处理结果
			while (rs.next()) {
				String str;
				str=rs.getString("name");
				list.add(str);
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

	public List<Subject> searchByBjId(int bjId) {
		List<Subject> list = new ArrayList<Subject>();
		getStatement();
		String sql = "select sub.* from  banji as bj "
				+ " inner join stu_cou as m on bj.id=m.bj_id"
				+ " inner join subject as sub on m.sub_id=sub.id"
				+ " where bj.id=" + bjId;
		try {
			rs = stat.executeQuery(sql);

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
