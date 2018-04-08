package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.Pagination;
import Dao.BanJiDao;
import Dao.ScoreDao;
import Dao.SubjectDao;
import Entity.BanJi;
import Entity.Score;
import Entity.Student;
import Entity.Subject;

public class ScoreServlet extends HttpServlet{
	ScoreDao scDao=new ScoreDao();
	BanJiDao bjDao=new BanJiDao();
	SubjectDao subDao=new SubjectDao();
	public void doGet(HttpServletRequest request,HttpServletResponse response){
		String type=request.getParameter("type");
		if(null==type){
			search(request,response);
		}else if(type.equals("search")){
			search(request,response);
		}else if(type.equals("manage")){
			manage(request,response);
		}else if(type.equals("input")){
			input(request,response);
		}
	}
	private void input(HttpServletRequest request, HttpServletResponse response) {
		try {
		int scId=Integer.parseInt(request.getParameter("scId"));
		int stuId=Integer.parseInt(request.getParameter("stuId"));
		int subId=Integer.parseInt(request.getParameter("subId"));
		int score=Integer.parseInt(request.getParameter("score"));
		Score sc=new Score();
		sc.setScore(score);
		Student stu=new Student();
		stu.setId(stuId);
		Subject sub=new Subject();
		sub.setId(subId);
		sc.setStu(stu);
		sc.setSub(sub);
		boolean flag=false;
		if(scId==0){
			flag=scDao.add(sc);
		}else{
			sc.setId(scId);
			flag=scDao.update(sc);
		}
		PrintWriter out = response.getWriter();
		if(flag){
			sc=scDao.searchScore(sc);
			JSONObject json=JSONObject.fromObject(sc);
			out.print(json);
		}else{
			out.print(flag);
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void common(HttpServletRequest request, HttpServletResponse response){
		String stuName=request.getParameter("stuName");
		int bjId=-1;
		if(!"".equals(request.getParameter("bj"))&&null!=request.getParameter("bj")){
			bjId=Integer.parseInt(request.getParameter("bj"));
		}
		int subId=-1;
		if(!"".equals(request.getParameter("sub"))&&null!=request.getParameter("sub")){
			subId=Integer.parseInt(request.getParameter("sub"));
		}
		Score condition=new Score();
		Student stu=new Student();
		Subject sub=new Subject();
		BanJi bj=new BanJi();
		sub.setId(subId);
		condition.setSub(sub);
		bj.setId(bjId);
		stu.setName(stuName);
		stu.setBanji(bj);
		condition.setStu(stu);
		int ye=1;
		if(null!=request.getParameter("ye")){
			ye=Integer.parseInt(request.getParameter("ye"));
		}
		int max=scDao.searchCount(condition);
		int yeNum=2;
		int yeMa=5;
		Pagination p=new Pagination(ye, max, yeNum, yeMa);
		ye=p.getYe();
		int begin=(ye-1)*yeNum;
		List<Score>list=scDao.searchByCondition(condition, begin, yeNum);
		List<BanJi> bjList=bjDao.searchAll();
		List<Subject>subList=subDao.searchByBjId(bjId);
		request.setAttribute("scs", list);
		request.setAttribute("bjs", bjList);
		request.setAttribute("subs", subList);
		request.setAttribute("p", p);
		request.setAttribute("condition", condition);
	}
	private void manage(HttpServletRequest request, HttpServletResponse response) {
		common(request,response);
		try {
			request.getRequestDispatcher("WEB-INF/score/manage.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void search(HttpServletRequest request, HttpServletResponse response) {
		common(request,response);
		try {
			request.getRequestDispatcher("WEB-INF/score/list.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response){
		doGet(request,response);
	}
}
