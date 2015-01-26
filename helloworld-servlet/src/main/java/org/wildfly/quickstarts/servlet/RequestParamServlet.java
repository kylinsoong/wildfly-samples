package org.wildfly.quickstarts.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wildfly.quickstarts.servlet.util.ServletUtil;

@WebServlet("/RequestParam")
public class RequestParamServlet extends HttpServlet {

	private static final long serialVersionUID = 7345596678719362119L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType(ServletUtil.contentType());
		
		PrintWriter out = resp.getWriter();
		
		out.println(ServletUtil.pageHeader("Request Parameters Example"));
		
		out.println("<h3>Request Parameters Example</h3>");
        out.println("Parameters in this request:<br>");
        
        String firstName = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        
        if (firstName != null || lastName != null) {
            out.println("First Name: " + firstName + "<br>");
            out.println("Last  Name: " + lastName + "<br>");
        } else {
            out.println("No Parameters, Please enter some");
        }
        out.println("<P>");
        out.print("<form action=\"");
        out.print("RequestParam\" ");
        out.println("method=POST>");
        out.println("First Name:");
        out.println("<input type=text size=20 name=firstname>");
        out.println("<br>");
        out.println("Last  Name:");
        out.println("<input type=text size=20 name=lastname>");
        out.println("<br>");
        out.println("<input type=submit>");
        out.println("</form>");
		
		out.println(ServletUtil.pageFooter());
						
		out.close();
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
