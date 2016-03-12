package org.wildfly.quickstarts.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LifeCycleServlet extends HttpServlet {

  
	private static final long serialVersionUID = 1770304184548479022L;
	
	@Override
	public void init() throws ServletException {
		
		System.out.println("Servlet init");
	}

	@Override
	public void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("Servlet service: " + request.getRequestURI());
	}


	@Override
	public void destroy() {
		System.out.println("Servlet destroy");
	}

	

	

}
