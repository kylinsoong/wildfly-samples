package com.kylin.msc.modular;

import java.io.File;

import org.jboss.modules.LocalModuleLoader;
import org.jboss.modules.Module;
import org.jboss.modules.ModuleLoader;
import org.jboss.modules.filter.PathFilters;

public class LocalModuleLoaderTest extends TestBase {
	
	protected void test() throws Exception {
		
		final File repoRoot = new File(RUNNABLE_MODULE_PATH);
		
		moduleLoader = new LocalModuleLoader(new File[]{repoRoot}, PathFilters.acceptAll());
		
		Module module = moduleLoader.loadModule(MODULE_ID);
		
		pauseln("Print ClassLoader Hierarchy");
//		new PrintClassLoader().printClassLoader(module.getClassLoader());
		
		pauseln("\nPrint ModuleLoader");
		ModuleLoader loader1 = Module.getBootModuleLoader();
		ModuleLoader loader2 = Module.getCallerModuleLoader();
//		ModuleLoader loader3 = Module.getContextModuleLoader();
		println(loader1);
		println(loader2);
//		println(loader3);
		
		
	}

	public static void main(String[] args) throws Exception {
		new LocalModuleLoaderTest().test();
	}

}
