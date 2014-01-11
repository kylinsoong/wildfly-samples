package org.jboss.modules.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 
 * 1. create module.xml add content as below:
 *        
      <?xml version="1.0" encoding="UTF-8"?>
      <module xmlns="urn:jboss:module:1.1" name="org.jboss.modules.props">
	    <resources>
	        <resource-root path="."/>
	    </resources>
	    <dependencies>
	    </dependencies>
	 </module>
	 
 * 2. create file test.properties and test.conf under org/jboss/modules/props/main, edit test.properties add below item:
 *       key=This is test props
 *  
 * 3. deploy test war, test
 * 
 * 4. useful link:
 *      https://community.jboss.org/wiki/HowToPutAnExternalFileInTheClasspath
 *          
 * @author kylin
 *
 */
@WebServlet("/ServiceModuleLoaderLoadProps")
public class ModularLoadingProps extends HttpServlet{

	private static final long serialVersionUID = 8827228158204067063L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		InputStream in = ModularLoadingProps.class.getClassLoader().getResourceAsStream("test.properties");
		Properties prop = new Properties();
		prop.load(in);
		System.out.println("Read test.properties Info: " + prop.get("key"));
		URL url = ModularLoadingProps.class.getClassLoader().getResource("test.conf");
		System.out.println("Print test.conf Path: " + url);
		in.close();
	}
	


	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
