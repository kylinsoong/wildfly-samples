package org.wildfly.quickstarts.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wildfly.quickstarts.servlet.util.ServletUtil;


@WebServlet("/Cookie")
public class CookieServlet extends HttpServlet {

	private static final long serialVersionUID = 3296141808820380126L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType(ServletUtil.contentType());
		
		PrintWriter out = resp.getWriter();
		
		out.println(ServletUtil.pageHeader("Cookie Test Example"));
		out.println("<h3>Cookie Test Example</h3>");
		
		Cookie[] cookies = req.getCookies();
        if ((cookies != null) && (cookies.length > 0)) {
            out.println("Your browser is sending the following cookies: " + "<br>");
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                out.print("Cookie Name: " + cookie.getName() + "<br>");
                out.println("Cookie Value: " + cookie.getValue() + "<br><br>");
            }
        } else {
            out.println("Your browser isn't sending any cookies");
        }

        String cookieName = req.getParameter("cookiename");
        String cookieValue = req.getParameter("cookievalue");
        if (cookieName != null && cookieValue != null) {
            Cookie cookie = new Cookie(cookieName, cookieValue);
            resp.addCookie(cookie);
            out.println("<P>");
            out.println("You just sent the following cookie to your browser: " + "<br>");
            out.print("Name: " + cookieName + "<br>");
            out.print("Value: " + cookieValue);
        }

        out.println("<P>");
        
        out.println("Create a cookie to send to your browser" + "<br>");
        out.print("<form action=\"");
        out.println("Cookie\" method=POST>");
        out.print("Name: " + "  ");
        out.println("<input type=text length=20 name=cookiename><br>");
        out.print("Value: " + "  ");
        out.println("<input type=text length=20 name=cookievalue><br>");
        out.println("<input type=submit></form>");
		
		out.println(ServletUtil.pageFooter());
		
		
		out.close();
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
