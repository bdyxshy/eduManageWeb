package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import util.Pagination;
import Dao.SubjectDao;
import Entity.Subject;

public class SubjectServlet extends HttpServlet {
	SubjectDao subDao=new SubjectDao();
	public void doGet(HttpServletRequest request ,HttpServletResponse response){
		String type=request.getParameter("type");
		if(type==null){
			search(request,response);
		}else if(type.equals("showAdd")){
			showAdd(request,response);
		}else if(type.equals("add")){
			add(request,response);
		}else if(type.equals("showModify")){
			showModify(request,response);
		}else if(type.equals("modify")){
			modify(request,response);
		}else if(type.equals("delete")){
			delete(request,response);
		}else if(type.equals("search")){
			search(request,response);
		}else if(type.equals("searchSubByBj")){
			searchSubByBj(request,response);
		}
	}
	private void searchSubByBj(HttpServletRequest request,
			HttpServletResponse response) {
		int bjId=Integer.parseInt(request.getParameter("bjId"));
		List<Subject> list=subDao.searchByBjId(bjId);
		JSONArray json=JSONArray.fromObject(list);
		try {
			PrintWriter out=response.getWriter();
			out.print(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void search(HttpServletRequest request, HttpServletResponse response) {
		SubjectDao subDao=new SubjectDao();
		Subject sub=new Subject();
		String name="";
		if(!"".equals(request.getParameter("name"))&& null!= request.getParameter("name")){
			name=request.getParameter("name");
		}
		sub.setName(name);
		int begin = 0;
		int ye=1;
		if(request.getParameter("ye")!=null){
			ye=Integer.parseInt(request.getParameter("ye"));
		}
		int max=subDao.searchCount(sub);
		int yeNum=2;
		int yeMa=5;
		Pagination pagination=new Pagination(ye, max,yeNum,yeMa);
		ye=pagination.getYe();
		begin=(ye-1)*yeNum;
		List<Subject>list =subDao.searchByCondition(sub,begin,yeNum);
		try {
			request.setAttribute("subs", list);
			request.setAttribute("p",pagination);
			request.setAttribute("sub",sub);
			request.getRequestDispatcher("WEB-INF/subject/list.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void delete(HttpServletRequest request, HttpServletResponse response) {
		SubjectDao subDao=new SubjectDao();
		int id=Integer.parseInt(request.getParameter("selectId"));
		boolean flag=subDao.delete(id);
		if(flag){
			try {
				response.sendRedirect("sub");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	private void modify(HttpServletRequest request, HttpServletResponse response) {
		SubjectDao subDao=new SubjectDao();
		int id=Integer.parseInt(request.getParameter("id"));
		String name=request.getParameter("name");
		Subject sub=new Subject();
		sub.setId(id);
		sub.setName(name);
		boolean flag=subDao.modify(sub);
		if(flag){
			try {
				response.sendRedirect("sub");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void showModify(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			SubjectDao subDao=new SubjectDao();
			int id=Integer.parseInt(request.getParameter("selectId"));
			Subject sub=subDao.searchById(id);
			request.setAttribute("sub",sub);
			request.getRequestDispatcher("WEB-INF/subject/modify.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void showAdd(HttpServletRequest request ,HttpServletResponse response){
		try {
			request.getRequestDispatcher("WEB-INF/subject/add.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void add(HttpServletRequest request ,HttpServletResponse response){
		try {
			String name=request.getParameter("name");
			Subject sub=new Subject();
			sub.setName(name);
			SubjectDao subDao=new SubjectDao();
			Boolean flag=subDao.add(sub);
			if(flag){
				response.sendRedirect("sub");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void show(HttpServletRequest request ,HttpServletResponse response){
		SubjectDao subDao=new SubjectDao();
		int begin = 0;
		int ye=1;
		if(request.getParameter("ye")!=null){
			ye=Integer.parseInt(request.getParameter("ye"));
		}
		int max=subDao.searchCount();
		int yeNum=2;
		int yeMa=5;
		Pagination pagination=new Pagination(ye, max,yeNum,yeMa);
		ye=pagination.getYe();
		begin=(ye-1)*yeNum;
		List<Subject> list=subDao.searchByBegin(begin,yeNum);
		try {
			request.setAttribute("subs",list);
			request.setAttribute("p",pagination);
			request.getRequestDispatcher("WEB-INF/subject/list.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void doPost(HttpServletRequest request ,HttpServletResponse response){
		doGet(request,response);
	}
}
