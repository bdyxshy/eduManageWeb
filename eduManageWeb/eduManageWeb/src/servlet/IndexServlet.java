package servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexServlet extends HttpServlet{
	public void doGet(HttpServletRequest request ,HttpServletResponse response){
		String type=request.getParameter("type");
		if(type==null){
			index(request,response);
		}else{
			try {
				Method method = this.getClass().getDeclaredMethod(type,
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
//		try {
//			if(type==null){
//				request.getRequestDispatcher("WEB-INF/index/index.jsp").forward(request, response);
//			}else if(type.equals("header")){
//				request.getRequestDispatcher("WEB-INF/index/header.jsp").forward(request, response);
//			}else if(type.equals("left")){
//				request.getRequestDispatcher("WEB-INF/index/left.jsp").forward(request, response);
//			}else if(type.equals("footer")){
//				request.getRequestDispatcher("WEB-INF/index/footer.jsp").forward(request, response);
//			}
//		} catch (ServletException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
 
	}
	public void index(HttpServletRequest request ,HttpServletResponse response){
		try {
			request.getRequestDispatcher("WEB-INF/index/index.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void header(HttpServletRequest request ,HttpServletResponse response){
		try {
			request.getRequestDispatcher("WEB-INF/index/header.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void footer(HttpServletRequest request ,HttpServletResponse response){
		try {
			request.getRequestDispatcher("WEB-INF/index/footer.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void left(HttpServletRequest request ,HttpServletResponse response){
		try {
			request.getRequestDispatcher("WEB-INF/index/left.jsp").forward(request, response);
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
