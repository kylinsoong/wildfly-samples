package org.wildfly.undertow.quickstart.servlet;

import javax.servlet.ServletException;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;

public class ServletServer {
	
	public static final String MYAPP = "/myapp";

	public static void main(String[] args) throws ServletException {
		
		DeploymentInfo servletBuilder = Servlets.deployment().setClassLoader(ServletServer.class.getClassLoader())
				                                             .setContextPath(MYAPP)
				                                             .setDeploymentName("myapp.war")
				                                             .addServlets(Servlets.servlet("MessageServlet", MessageServlet.class).addMappings("/messageServlet")
				                                            		    , Servlets.servlet("MyServlet", MyServlet.class).addMappings("/myServlet"));
		DeploymentManager manager = Servlets.defaultContainer().addDeployment(servletBuilder);
		manager.deploy();
		
		HttpHandler servletHandler = manager.start();
		PathHandler path = Handlers.path(Handlers.redirect(MYAPP)).addPrefixPath(MYAPP, servletHandler);
		
		Undertow server = Undertow.builder().addHttpListener(8080, "localhost").setHandler(path).build();
		server.start();
	}

}
