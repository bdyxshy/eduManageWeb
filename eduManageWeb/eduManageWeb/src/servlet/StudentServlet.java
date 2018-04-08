package servlet;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import util.Pagination;
import Dao.BanJiDao;
import Dao.StudentDao;
import Entity.BanJi;
import Entity.Student;

public class StudentServlet extends HttpServlet {
	BanJiDao bjDao=new BanJiDao();
	StudentDao stuDao=new StudentDao();
	public void doGet(HttpServletRequest request ,HttpServletResponse response){
		String type=request.getParameter("type");
		if(type==null){
			search(request,response);
		}else{
			Method method;
			try {
				method = this.getClass().getDeclaredMethod(type,
						HttpServletRequest.class,HttpServletResponse.class);
				method.invoke(this,request,response);
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		if(type==null){
//			search(request,response);
//		}else if(type.equals("showAdd")){
//			showAdd(request,response);
//		}else if(type.equals("add")){
//			add(request,response);
//		}else if(type.equals("showModify")){
//			showModify(request,response);
//		}else if(type.equals("modify")){
//			modify(request,response);
//		}else if(type.equals("delete")){
//			delete(request,response);
//		}else if(type.equals("search")){
//			search(request,response);
//		}

	}
	private void search(HttpServletRequest request, HttpServletResponse response) {
		StudentDao stuDao=new StudentDao();
		Student stu=new Student();
		BanJi bj=new BanJi();
		String name="";
		String sex="";
		int age=-1;
		int bjId=-1;
		if(!"".equals(request.getParameter("name"))&& null!= request.getParameter("name")){
			name=request.getParameter("name");
		}
		if("ÄÐ".equals(request.getParameter("sex"))||("Å®").equals(request.getParameter("sex"))){
			sex=request.getParameter("sex");
		}
		if(""!=(request.getParameter("age"))&&null!= request.getParameter("age")){
			age=Integer.parseInt(request.getParameter("age"));
		}
		if(""!=(request.getParameter("bj"))&&null!= request.getParameter("bj")){
			bjId=Integer.parseInt(request.getParameter("bj"));
		}
		stu.setName(name);
		stu.setSex(sex);
		stu.setAge(age);
		bj.setId(bjId);
		stu.setBanji(bj);
		int begin = 0;
		int ye=1;
		if(request.getParameter("ye")!=null){
			ye=Integer.parseInt(request.getParameter("ye"));
		}
		int max=stuDao.searchCount(stu);
		int yeNum=2;
		int yeMa=5;
		Pagination pagination=new Pagination(ye, max,yeNum,yeMa);
		ye=pagination.getYe();
		begin=(ye-1)*yeNum;
		List<Student>list =stuDao.searchByCondition(stu,begin,yeNum);
		List<BanJi> list2=bjDao.searchAll();
		try {
			request.setAttribute("bjs", list2);
			request.setAttribute("stus", list);
			request.setAttribute("p",pagination);
			request.setAttribute("stu",stu);
			request.getRequestDispatcher("WEB-INF/student/list.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void delete(HttpServletRequest request, HttpServletResponse response) {
		StudentDao stuDao=new StudentDao();
		int id=Integer.parseInt(request.getParameter("selectId"));
		Student stu=stuDao.searchById(id);
		String url=request.getServletContext().getRealPath("/")+"/photos/";
		String photo=stu.getPhoto();
		if(!"".equals(photo)&&null!=photo){
			String pic=url+photo;
			File file=new File(pic);
			if(file.exists()){
				file.delete();
			}
		}
		boolean flag=stuDao.delete(id);
		if(flag){
			try {
				response.sendRedirect("stu");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	private void modify(HttpServletRequest request, HttpServletResponse response) {
		try {
			int id=0;
			String name="";
			name=request.getParameter("name");
			String sex="";
			int age=-1;
			int bjId=0;
			String newFileName="";
			FileItemFactory factory=new DiskFileItemFactory();
			ServletFileUpload upload=new ServletFileUpload(factory);
			List<FileItem>items=upload.parseRequest(request);
			String url=request.getServletContext().getRealPath("/")+"/photos/";
			for(int i=0;i<items.size();i++){
				if("id".equals(items.get(i).getFieldName())){
					id=Integer.parseInt(items.get(i).getString());
				}else if("name".equals(items.get(i).getFieldName())){
					name=new String(items.get(i).getString().getBytes("ISO-8859-1"),"utf-8");
				}if("sex".equals(items.get(i).getFieldName())){
					sex=new String(items.get(i).getString().getBytes("ISO-8859-1"),"utf-8");
				}else if("age".equals(items.get(i).getFieldName())){
					age=Integer.parseInt(items.get(i).getString());
				}else if("bj".equals(items.get(i).getFieldName())){
					bjId=Integer.parseInt(items.get(i).getString());
				}else if("photo".equals(items.get(i).getFieldName())){
					if(items.get(i).getName().length()>0){
						UUID uuid=UUID.randomUUID();
						String s=uuid.toString();
						String oldFileName=items.get(i).getName();
						String houzhui=oldFileName.substring(oldFileName.lastIndexOf("."));
						newFileName=s+houzhui;
						File file=new File(url+"/"+newFileName);
						items.get(i).write(file);
					}else{
						Student stu1=stuDao.searchById(id);
						newFileName=stu1.getPhoto();
					}
				}
			}
		
			Student stu=new Student();
			stu.setId(id);
			stu.setName(name);
			stu.setSex(sex);
			stu.setAge(age);
			BanJi bj=new BanJi();
			bj.setId(bjId);
			stu.setBanji(bj);
			stu.setPhoto(newFileName);
			Boolean flag=stuDao.modify(stu);
			if(flag){
				response.sendRedirect("stu");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void showModify(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			StudentDao stuDao=new StudentDao();
			int id=Integer.parseInt(request.getParameter("selectId"));
			Student stu=stuDao.searchById(id);
			request.setAttribute("stu",stu);
			List<BanJi> list=bjDao.searchAll();
			request.setAttribute("bjs", list);
			request.getRequestDispatcher("WEB-INF/student/modify.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void showAdd(HttpServletRequest request ,HttpServletResponse response){
		try {
			List<BanJi> list=bjDao.searchAll();
			request.setAttribute("bjs", list);
			request.getRequestDispatcher("WEB-INF/student/add.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void add(HttpServletRequest request ,HttpServletResponse response){
		try {
			String name="";
				name=request.getParameter("name");
			String sex="";
			int age=-1;
			int bjId=0;
			String newFileName="";
			FileItemFactory factory=new DiskFileItemFactory();
			ServletFileUpload upload=new ServletFileUpload(factory);
			List<FileItem>items=upload.parseRequest(request);
			String url=request.getServletContext().getRealPath("/")+"/photos/";
			for(int i=0;i<items.size();i++){
				if("name".equals(items.get(i).getFieldName())){
					name=new String(items.get(i).getString().getBytes("ISO-8859-1"),"utf-8");
				}else if("sex".equals(items.get(i).getFieldName())){
					sex=new String(items.get(i).getString().getBytes("ISO-8859-1"),"utf-8");
				}else if("age".equals(items.get(i).getFieldName())){
					age=Integer.parseInt(items.get(i).getString());
				}else if("bj".equals(items.get(i).getFieldName())){
					bjId=Integer.parseInt(items.get(i).getString());
				}else if("photo".equals(items.get(i).getFieldName())){
					UUID uuid=UUID.randomUUID();
					String s=uuid.toString();
					String oldFileName=items.get(i).getName();
					String houzhui=oldFileName.substring(oldFileName.lastIndexOf("."));
					newFileName=s+houzhui;
					File file=new File(url+"/"+newFileName);
					items.get(i).write(file);
				}
			}
		
			Student stu=new Student();
			stu.setName(name);
			stu.setSex(sex);
			stu.setAge(age);
			BanJi bj=new BanJi();
			bj.setId(bjId);
			stu.setBanji(bj);
			stu.setPhoto(newFileName);
			StudentDao stuDao=new StudentDao();
			Boolean flag=stuDao.add(stu);
			if(flag){
				response.sendRedirect("stu");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void show(HttpServletRequest request ,HttpServletResponse response){
		StudentDao stuDao=new StudentDao();
		int begin = 0;
		int ye=1;
		if(request.getParameter("ye")!=null){
			ye=Integer.parseInt(request.getParameter("ye"));
		}
		int max=stuDao.searchCount();
		int yeNum=2;
		int yeMa=5;
		Pagination pagination=new Pagination(ye, max,yeNum,yeMa);
		ye=pagination.getYe();
		begin=(ye-1)*yeNum;
		List<Student> list=stuDao.searchByBegin(begin,yeNum);
		try {
			request.setAttribute("stus",list);
			request.setAttribute("p",pagination);
			request.getRequestDispatcher("WEB-INF/student/list.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void doPost(HttpServletRequest request ,HttpServletResponse response){
		doGet(request,response);
	}
}
