package org.jboss.web.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.net.URLDecoder;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/image/*", initParams = { @WebInitParam(name = "basePath", value = "/tmp/img") })
public class FileServlet extends HttpServlet {

	private static final long serialVersionUID = -3249597492052107066L;
	
    private String basePath;
    
    public void init() throws ServletException {

        this.basePath = getInitParameter("basePath");
        
        // Validate base path.
        if (this.basePath == null) {
            throw new ServletException("FileServlet init param 'basePath' is required.");
        } else {
            File path = new File(this.basePath);
            if (!path.exists()) {
                throw new ServletException("FileServlet init param 'basePath' value '" + this.basePath + "' does actually not exist in file system.");
            } else if (!path.isDirectory()) {
                throw new ServletException("FileServlet init param 'basePath' value '" + this.basePath + "' is actually not a directory in file system.");
            } else if (!path.canRead()) {
                throw new ServletException("FileServlet init param 'basePath' value '" + this.basePath + "' is actually not readable in file system.");
            }
        }
    }
    

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String requestedFile = request.getPathInfo();
		
		File file = new File(basePath, URLDecoder.decode(requestedFile, "UTF-8"));
		if (!file.exists()) {
            // Throw 404, redirect to error page may is another selection
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
		
		// write via response's OutputStream
		FileInputStream inputStream = null;

		try {
			inputStream = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			int bytesRead = 0;

			do {
				bytesRead = inputStream.read(buffer, 0, buffer.length);
				response.getOutputStream().write(buffer, 0, bytesRead);
			} while (bytesRead == buffer.length);

			response.getOutputStream().flush();
		} finally {
			if (inputStream != null)
				inputStream.close();
		}
	}
    
   

}
