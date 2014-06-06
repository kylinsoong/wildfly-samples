package org.wildfly.domain.test;

import java.util.Collections;

import org.jboss.as.server.Bootstrap;
import org.jboss.as.server.Main;
import org.jboss.as.server.ServerEnvironment;
import org.jboss.modules.Module;
import org.jboss.modules.ModuleIdentifier;
import org.jboss.modules.StandaloneStartModule;
import org.jboss.msc.service.ServiceActivator;
import org.jboss.msc.service.ServiceContainer;

public class StandaloneStartUP {
	
	static String[] moduleArgs = new String[]{"-Djboss.home.dir=/home/kylin/work/eap/jboss-eap-6.1", "-Djboss.server.base.dir=/home/kylin/work/eap/jboss-eap-6.1/standalone"};


	public static void main(String[] args) throws Exception {


		StandaloneStartModule.initBootModule();
		
		Module.registerURLStreamHandlerFactoryModule(Module.getBootModuleLoader().loadModule(ModuleIdentifier.create("org.jboss.vfs")));
		
		ServerEnvironment serverEnvironment = Main.determineEnvironment(moduleArgs, System.getProperties(), System.getenv(), ServerEnvironment.LaunchType.STANDALONE);
		
		final Bootstrap bootstrap = Bootstrap.Factory.newInstance();
		final Bootstrap.Configuration configuration = new Bootstrap.Configuration(serverEnvironment);
		configuration.setModuleLoader(Module.getBootModuleLoader());
		ServiceContainer container = bootstrap.bootstrap(configuration, Collections.<ServiceActivator>emptyList()).get();
		
		System.out.println(container);
	}

}
