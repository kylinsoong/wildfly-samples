package org.jboss.modules.examples.swarm;

import org.jboss.modules.Module;
import org.jboss.modules.ModuleClassLoader;
import org.jboss.modules.ModuleIdentifier;
import org.jboss.modules.ModuleLoadException;
import org.jboss.modules.ModuleLoader;
import org.wildfly.swarm.bootstrap.modules.BootModuleLoader;

/**
 * This class used to debug loading translator module issue
 * <pre>
 * 2016-09-30 15:44:34,321 ERROR [org.jboss.as.controller.management-operation] (ServerService Thread Pool -- 17) WFLYCTL0013: Operation ("add") failed - address: ([
 *   ("subsystem" => "teiid"),
 *   ("translator" => "h2")
 * ]) - failure description: "TEIID50007 Failed to load module org.jboss.teiid.translator.jdbc for translator h2"
 * </pre>
 * 
 * @author kylin
 *
 */
public class ReproduceLoadingTranslator {

    public static void main(String[] args) throws ModuleLoadException, ClassNotFoundException {

        System.setProperty("swarm.isuberjar", Boolean.TRUE.toString());
        System.setProperty("boot.module.loader", BootModuleLoader.class.getName());
        
        Module module = Module.getBootModuleLoader().loadModule(ModuleIdentifier.create("org.jboss.teiid"));
        Class<?> cls = module.getClassLoader().loadClass("org.teiid.dqp.message.RequestID");
        ModuleClassLoader translatorLoader = (ModuleClassLoader) cls.getClassLoader();
//        ClassLoader translatorLoader = module.getClassLoader();
//                
        ModuleIdentifier id = ModuleIdentifier.create("org.jboss.teiid.translator.jdbc");
        System.out.println(module.getModuleLoader().loadModule(id));
    }

}
