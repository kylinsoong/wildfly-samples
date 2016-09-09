package org.jboss.modules.examples;

import org.jboss.modules.Module;
import org.jboss.modules.ModuleFinder;
import org.jboss.modules.ModuleIdentifier;
import org.jboss.modules.ModuleLoadException;
import org.jboss.modules.ModuleLoader;
import org.jboss.modules.log.ModuleLogger;
import org.wildfly.swarm.bootstrap.modules.BootModuleLoader;
import org.wildfly.swarm.bootstrap.modules.BootstrapClasspathModuleFinder;


public class ModuleTest {

    public static void main(String[] args) throws ModuleLoadException, ClassNotFoundException {
        
        System.setProperty("boot.module.loader", BootModuleLoader.class.getName());
        
        Module.setModuleLogger(new ModuleLogger(){

            @Override
            public void trace(String message) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void trace(String format, Object arg1) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void trace(String format, Object arg1, Object arg2) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void trace(String format, Object arg1, Object arg2,
                    Object arg3) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void trace(String format, Object... args) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void trace(Throwable t, String message) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void trace(Throwable t, String format, Object arg1) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void trace(Throwable t, String format, Object arg1,
                    Object arg2) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void trace(Throwable t, String format, Object arg1,
                    Object arg2, Object arg3) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void trace(Throwable t, String format, Object... args) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void greeting() {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void moduleDefined(ModuleIdentifier identifier,
                    ModuleLoader moduleLoader) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void classDefineFailed(Throwable throwable,
                    String className, Module module) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void classDefined(String name, Module module) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void providerUnloadable(String name, ClassLoader loader) {
                // TODO Auto-generated method stub
                
            }});
        
        Module module = Module.getBootModuleLoader().loadModule(ModuleIdentifier.create("swarm.application"));

        System.out.println(module);
        
//        ModuleLoader loader = Module.getBootModuleLoader();
//        System.out.println(loader);
//        
//        ModuleIdentifier identifier = ModuleIdentifier.create("javax.api");
//        Module module = loader.loadModule(identifier);
//        module.getClassLoader().loadClass("No-exist");
//        System.out.println(module);
    }
    
    public static class MyModuleLoader extends ModuleLoader {
        
        public MyModuleLoader(){
            super(new ModuleFinder[]{
                    new BootstrapClasspathModuleFinder(),
//                    new BootstrapModuleFinder()
            });
        }
        
    }
    

}
