package Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Entity.BanJi;
import Entity.Score;
import Entity.Student;
import Entity.Subject;

public class ScoreDao extends BaseDao{
	public List<Score> searchByCondition(Score condition,int begin,int num){
		List<Score>list=new ArrayList<Score>();
		try {
			getStatement();
			String where=" where 1=1 ";
			if(!"".equals(condition.getStu().getName())&&null!=condition.getStu().getName()){
				where+=" and stuName like '%"+condition.getStu().getName()+"%'";
			}
			if(condition.getStu().getBanji().getId()!=-1){
				where+=" and bjId="+condition.getStu().getBanji().getId();
			}
			if(condition.getSub().getId()!=-1){
				where+=" and subId="+condition.getSub().getId();
			}
			String sql="select * from v_stu_bj_sub_score"+where+" limit "+ begin+","+num;
			rs=stat.executeQuery(sql);
			while(rs.next()){
				Score sc=new Score();
				sc.setId(rs.getInt("scId"));
				sc.setScore((Integer)rs.getObject("scScore"));
				sc.setGrade(rs.getString("scGrade"));
				Student stu=new Student();
				stu.setId(rs.getInt("stuId"));
				stu.setName(rs.getString("stuName"));
				BanJi bj=new BanJi();
				bj.setId(rs.getInt("bjId"));
				bj.setName(rs.getString("bjName"));
				Subject sub=new Subject();
				sub.setId(rs.getInt("subId"));
				sub.setName(rs.getString("subName"));
				stu.setBanji(bj);
				sc.setStu(stu);
				sc.setSub(sub);
				list.add(sc);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return list;
	}

	public int searchCount(Score condition) {
		List<Score>list=new ArrayList<Score>();
		int result=0;
		try {
			getStatement();
			String where=" where 1=1 ";
			if(!"".equals(condition.getStu().getName())&&null!=condition.getStu().getName()){
				where+=" and stuName like '%"+condition.getStu().getName()+"%'";
			}
			if(condition.getStu().getBanji().getId()!=-1){
				where+=" and bjId="+condition.getStu().getBanji().getId();
			}
			if(condition.getSub().getId()!=-1){
				where+=" and subId="+condition.getSub().getId();
			}
			String sql="select count(*) from v_stu_bj_sub_score"+where;
			rs=stat.executeQuery(sql);
			if(rs.next()){
				result=rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return result;
	}

	public boolean add(Score sc) {
		boolean flag=false;
		getStatement();
		String sql="insert into score (stu_id,sub_id,score) values("
		+sc.getStu().getId()+","+sc.getSub().getId()+","+sc.getScore()+")";
		try {
			int result=stat.executeUpdate(sql);
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

	public boolean update(Score sc) {
		boolean flag=false;
		getStatement();
		String sql="update score set score="+sc.getScore()+" where id="+sc.getId();
		try {
			int result=stat.executeUpdate(sql);
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

	public Score searchScore(Score sc) {
		Score score=null;
		try {
			getStatement();
			String sql="select * from score where stu_id="
					+sc.getStu().getId()+" and sub_id="+sc.getSub().getId();
			rs=stat.executeQuery(sql);
			while(rs.next()){
				score=new Score();
				score.setId(rs.getInt("id"));
				score.setScore((Integer)rs.getObject("score"));
				score.setGrade(rs.getString("grade"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeAll();
		}
		return score;
	}
}
