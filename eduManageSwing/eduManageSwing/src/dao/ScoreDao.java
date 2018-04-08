package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import entity.BanJi;
import entity.Score;
import entity.Student;
import entity.Subject;

public class ScoreDao extends BaseDao {

	public List<Score> searchAll() {

		List<Score> list = new ArrayList<Score>();

		try {

			getStatement();
			String sql = "select * from v_stu_bj_sub_score";
			rs = stat.executeQuery(sql);
			while (rs.next()) {

				Score sc = new Score();
				sc.setId(rs.getInt("scId"));
				if (rs.getString("score") != null) {
					sc.setScore(rs.getInt("score"));
				} else {
					sc.setScore(-1);

				}
				//sc.setGrade(rs.getString("grade"));
				Student stu = new Student();
				stu.setId(rs.getInt("stuId"));
				stu.setName(rs.getString("stuName"));
				BanJi bj = new BanJi();
				bj.setId(rs.getInt("bjId"));
				bj.setName(rs.getString("bjName"));
				stu.setBj(bj);
				sc.setStudent(stu);
				Subject sub = new Subject();
				sub.setId(rs.getInt("subId"));
				sub.setName(rs.getString("subName"));
				sc.setSubject(sub);
				list.add(sc);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll();
		}

		return list;
	}

	public List<Score> searchByCondition(Score condition) {

		List<Score> list = new ArrayList<Score>();

		try {

			getStatement();
			String where = "";

			if (!condition.getStudent().getName().equals("")) {
				where += " and  stuName='" + condition.getStudent().getName() + "'";
			}
			if (condition.getStudent().getBj().getId() != -1) {
				where += " and  bjId=" + condition.getStudent().getBj().getId();

			}
			if (condition.getSubject().getId() != -1) {
				where += " and  subId=" + condition.getSubject().getId();

			}

			String sql = "select * from v_stu_bj_sub_score where 1=1 " + where;
			rs = stat.executeQuery(sql);
			while (rs.next()) {

				Score sc = new Score();
				sc.setId(rs.getInt("scId"));
				if (rs.getString("score") != null) {
					sc.setScore(rs.getInt("score"));
				} else {
					sc.setScore(-1);

				}
				//sc.setGrade(rs.getString("grade"));
				Student stu = new Student();
				stu.setId(rs.getInt("stuId"));
				stu.setName(rs.getString("stuName"));
				BanJi bj = new BanJi();
				bj.setId(rs.getInt("bjId"));
				bj.setName(rs.getString("bjName"));
				stu.setBj(bj);
				sc.setStudent(stu);
				Subject sub = new Subject();
				sub.setId(rs.getInt("subId"));
				sub.setName(rs.getString("subName"));
				sc.setSubject(sub);
				list.add(sc);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll();
		}

		return list;
	}

	public boolean save(Set<Score> set) {
		boolean flag = true;
		for (Score sc : set) {

			if (sc.getId() == 0) {
				flag = add(sc);
			} else {

				flag = update(sc);
			}

			if (flag == false) {
				break;
			}

		}

		return flag;
	}

	private boolean update(Score sc) {
		boolean flag = false;

		try {
			String sql = "update score set score=? where id=?";
			getPreparedStatement(sql);
			pstat.setInt(1, sc.getScore());
			pstat.setInt(2, sc.getId());
			int result = pstat.executeUpdate();
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

	private boolean add(Score sc) {
		boolean flag = false;

		try {
			String sql = "insert into score (stu_id,cou_id,score) values(?,?,?)";
			getPreparedStatement(sql);
			pstat.setInt(1, sc.getStudent().getId());

			pstat.setInt(2, sc.getSubject().getId());
			pstat.setInt(3, sc.getScore());

			int result = pstat.executeUpdate();
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

}
