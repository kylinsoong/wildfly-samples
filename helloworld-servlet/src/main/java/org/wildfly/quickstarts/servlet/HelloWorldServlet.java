package org.wildfly.quickstarts.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wildfly.quickstarts.servlet.util.ServletUtil;

@WebServlet("/HelloWorld")
public class HelloWorldServlet extends HttpServlet {

  
	private static final long serialVersionUID = 1770304184548479022L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType(ServletUtil.contentType());
		
		PrintWriter out = resp.getWriter();
		
		out.println(ServletUtil.pageHeader("HelloWorld"));
		out.println("<h1>" + ServletUtil.createHelloMessage("World") + "</h1>");
		out.println(ServletUtil.pageFooter());
		
		
		out.close();
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
