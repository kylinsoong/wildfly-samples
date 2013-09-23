package org.jboss.modules.quickstart.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Util {

	public static byte[] readBytes(final InputStream is) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            byte[] buff = new byte[1024];
            int read;
            while((read = is.read(buff)) > -1) {
                os.write(buff, 0, read);
            }
        } finally {
            is.close();
        }
        return os.toByteArray();
    }
    
    public static String getResourceNameOfClass(final Class<?> aClass) throws IllegalArgumentException {
        final String nameAsResourcePath = aClass.getName().replace('.', '/');
        final String resourceName = nameAsResourcePath + ".class";
        return resourceName;
    }
    
    public static File getResourceFile(final Class<?> baseClass, final String path) throws Exception {
        return new File(getResource(baseClass, path).toURI());
    }
    
    public static URL getResource(final Class<?> baseClass, final String path) throws Exception {
        final URL url = baseClass.getClassLoader().getResource(path);
        return url;
    }
}
