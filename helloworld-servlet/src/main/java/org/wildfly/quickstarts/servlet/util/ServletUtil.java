package org.wildfly.quickstarts.servlet.util;

public class ServletUtil {
	
	public static String createHelloMessage(String name) {
		return "Hello " + name + "!";
	}

	public static String pageHeader(String title) {
		return "<html><head><title>" + title +"</title><body>";
	}
	   
	public static String pageFooter() {
		return "</body></html>";
	}
		
	public static String contentType() {
		return "text/html";
	}
	
	public static String servletReturnStr() {
		return "&#160;&#160;&#160;<a href=\"/helloworld-servlet/servlets/index.html\">Back</a>" ;
	}

}
