package org.jboss.modules;

import java.lang.reflect.InvocationTargetException;
import java.security.Policy;

import org.jboss.modules.ModuleIdentifier;
import org.jboss.modules.ModuleLoader;
import org.jboss.modules.log.JDKModuleLogger;

import __redirected.__JAXPRedirected;

public class StandaloneStartModule {
	
	public static void initBootModule() throws ModuleLoadException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
		StandaloneStartModule.main(null);
	}

	/*
	 * Simulate jboss module startup
	 */
	public static void main(String[] args) throws ModuleLoadException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {

		System.setProperty("module.path", "/home/kylin/work/eap/jboss-eap-6.1/modules");
		
		ModuleIdentifier jaxpModuleIdentifier = ModuleIdentifier.fromString("javax.xml.jaxp-provider");
		
		String nameArgument = "org.jboss.as.standalone";
		
		String[] moduleArgs = new String[]{"-Djboss.home.dir=/home/kylin/work/eap/jboss-eap-6.1", "-Djboss.server.base.dir=/home/kylin/work/eap/jboss-eap-6.1/standalone"};
		
		final ModuleLoader environmentLoader = DefaultBootModuleLoaderHolder.INSTANCE;
		
		final ModuleLoader loader = environmentLoader ;
		
		final ModuleIdentifier moduleIdentifier = ModuleIdentifier.fromString(nameArgument);
		
		Module.initBootModuleLoader(loader);
		
		__JAXPRedirected.changeAll(jaxpModuleIdentifier, Module.getBootModuleLoader());
		
		final Module module = loader.loadModule(moduleIdentifier);
		
		ModularURLStreamHandlerFactory.addHandlerModule(module);
        ModularContentHandlerFactory.addHandlerModule(module);
        
        Policy.setPolicy(new ModulesPolicy(Policy.getPolicy()));
        
        final ModuleClassLoader bootClassLoader = module.getClassLoaderPrivate();
        Thread.currentThread().setContextClassLoader(bootClassLoader);
        
        System.setProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager");
        Module.setModuleLogger(new JDKModuleLogger());
        
//        module.run(moduleArgs);
		
//		System.out.println(bootClassLoader);
	}

}
