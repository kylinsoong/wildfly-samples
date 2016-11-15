package org.jboss.modules.examples.test;

import java.io.File;


import org.jboss.modules.LocalModuleLoader;
import org.jboss.modules.Module;
import org.jboss.modules.ModuleIdentifier;
import org.jboss.modules.ModuleLoader;

public class TestBootModuleLoader {

    public static void main(String[] args) throws Exception {

        ModuleLoader bootLoader = Module.getBootModuleLoader();
        System.out.println(bootLoader);
     
        
        bootLoader = new LocalModuleLoader(new File[]{new File("src/main/resources/modules")});
        
        System.out.println(bootLoader);
        System.out.println(Module.getCallerModule());
        
        ModuleIdentifier id = ModuleIdentifier.fromString("javax.api");
        Module module = bootLoader.loadModule(id);
        System.out.println(module);
        
        
        Class<?> cls = module.getClassLoader().loadClass("javax.sql.RowSet");
        System.out.println(cls);
        
    }

}
