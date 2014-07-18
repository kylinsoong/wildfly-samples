package org.jboss.vfs.mount;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.jboss.vfs.TempFileProvider;
import org.jboss.vfs.VFS;
import org.jboss.vfs.VirtualFile;

public class VFSMount {

	public static void main(String[] args) throws IOException {

		mounts.putIfAbsent(parent, Collections.singletonMap(name, mount));
		
		reproduce();
		
	}
	
	static ConcurrentMap<String, Map<String, String>> mounts = new ConcurrentHashMap<String, Map<String, String>>();

	static String parent = "/home/kylin/work/eap/jboss-eap-6.2/standalone";
	static String name = "mylib.jar";
	static String mount = "This is Mount Value";
	
	private static String reproduce() throws IOException {

		
		
		
		Map<String, String> childMountMap = mounts.get(parent);
        Map<String, String> newMap;
        if (childMountMap == null) {
        	newMap = mounts.putIfAbsent(parent, Collections.singletonMap(name, mount));
        	if (childMountMap == null) {
        		return mount;
        	}
        	System.out.println(newMap);
        }
        newMap = new HashMap<String, String>(childMountMap);
        if (newMap.put(name, mount) != null) {
        	throw new IOException("VFS000017: Filesystem already mounted at mount point /home/kylin/work/eap/jboss-eap-6.2/standalone/mylib.jar");
        }
        
        return mount;
	}

}
