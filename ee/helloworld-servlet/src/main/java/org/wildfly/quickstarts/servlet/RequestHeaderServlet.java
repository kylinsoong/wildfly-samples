package org.wildfly.quickstarts.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wildfly.quickstarts.servlet.util.ServletUtil;

@WebServlet("/RequestHeader")
public class RequestHeaderServlet extends HttpServlet{

	private static final long serialVersionUID = 649387125992625387L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType(ServletUtil.contentType());

		PrintWriter out = resp.getWriter();
		
		out.println(ServletUtil.pageHeader("Request Header Example"));
		
		out.println("<h3>Request Header Example</h3>");
		
		Enumeration e = req.getHeaderNames();
        while (e.hasMoreElements()) {
            String name = (String)e.nextElement();
            String value = req.getHeader(name);
            out.println(name + ": " + value + "<br>");
        }
		
		out.println(ServletUtil.pageFooter());
				
		out.close();
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
	
}
