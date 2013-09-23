package org.jboss.modules.quickstart.test;

import org.jboss.modules.ModuleIdentifier;
import org.jboss.modules.ModuleLoader;

public abstract class TestBase {
	
	protected static final ModuleIdentifier MODULE_ID = ModuleIdentifier.fromString("com.kylin.msc.boot");
	
	protected static final String RUNNABLE_MODULE_PATH = "/home/kylin/work/project/JVM/msc/helloworld/build/helloworld/modules";
	protected static final String MODULE_PATH = "resources/modules";
	
	protected ModuleLoader moduleLoader;
	
	protected abstract void test() throws Exception;
	
	protected void println(Object obj) {
		System.out.println(obj);
	}
	
	protected void pauseln(Object obj) {
		System.out.println();
		System.out.println(obj);
		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
