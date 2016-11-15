package org.jboss.modules;

import org.jboss.modules.Module;
import org.jboss.modules.ModuleIdentifier;
import org.wildfly.swarm.bootstrap.modules.BootModuleLoader;

public class ReproduceBootStrap_1 {

    public static void main(String[] args) throws Exception {

        System.setProperty("swarm.isuberjar", Boolean.TRUE.toString());
        System.setProperty("boot.module.loader", BootModuleLoader.class.getName());
        
        Module module = Module.getBootModuleLoader().loadModule(ModuleIdentifier.create("org.wildfly.swarm.configuration.teiid", "api"));
        
        Class<?> clazz = module.loadModuleClass("org.wildfly.swarm.config.teiid.TranslatorConsumer", false);
        System.out.println(clazz);
    }

}
