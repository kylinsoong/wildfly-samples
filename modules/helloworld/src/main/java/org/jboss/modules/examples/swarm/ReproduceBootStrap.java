package org.jboss.modules.examples.swarm;

import java.lang.reflect.Method;

import org.jboss.modules.Module;
import org.jboss.modules.ModuleIdentifier;
import org.wildfly.swarm.bootstrap.modules.BootModuleLoader;

/**
 * This class is for resolving swarm bootstrap error
 * <pre>
 * Exception in thread "main" java.lang.reflect.InvocationTargetException
    at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
    at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
    at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
    at java.lang.reflect.Method.invoke(Method.java:483)
    at org.wildfly.swarm.bootstrap.Main.invoke(Main.java:86)
    at org.wildfly.swarm.bootstrap.Main.run(Main.java:50)
    at org.wildfly.swarm.bootstrap.Main.main(Main.java:45)
Caused by: java.lang.LinkageError: Failed to link org/wildfly/swarm/config/teiid/TranslatorConsumer (Module "org.wildfly.swarm.configuration.teiid:api" from BootModuleLoader@589b3632 for finders [BootstrapClasspathModuleFinder, BootstrapModuleFinder(org.wildfly.swarm.bootstrap:main), ClasspathModuleFinder, ContainerModuleFinder(swarm.container:main), ApplicationModuleFinder(swarm.application:main), FlattishApplicationModuleFinder(swarm.application:flattish)]): loader constraint violation: loader (instance of org/jboss/modules/ModuleClassLoader) previously initiated loading for a different type with name "org/wildfly/swarm/config/teiid/TranslatorConsumer"
    at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
    at sun.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
    at sun.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
    at java.lang.reflect.Constructor.newInstance(Constructor.java:408)
    at org.jboss.modules.ModuleClassLoader.defineClass(ModuleClassLoader.java:446)
    at org.jboss.modules.ModuleClassLoader.loadClassLocal(ModuleClassLoader.java:274)
    at org.jboss.modules.ModuleClassLoader$1.loadClassLocal(ModuleClassLoader.java:78)
    at org.jboss.modules.Module.loadModuleClass(Module.java:605)
    at org.jboss.modules.ModuleClassLoader.findClass(ModuleClassLoader.java:190)
    at org.jboss.modules.ConcurrentClassLoader.performLoadClassUnchecked(ConcurrentClassLoader.java:363)
    at org.jboss.modules.ConcurrentClassLoader.performLoadClass(ConcurrentClassLoader.java:351)
    at org.jboss.modules.ConcurrentClassLoader.loadClass(ConcurrentClassLoader.java:93)
    at org.wildfly.swarm.config.Teiid.translator(Teiid.java:149)
    at org.wildfly.swarm.teiid.examples.Main.main(Main.java:20)
 * </pre>
 * 
 * @author kylin
 *
 */
public class ReproduceBootStrap {
    
    static {
        System.setProperty("swarm.log.org.wildfly.swarm", "ALL");
    }

    public static void main(String[] args) throws Exception {

        System.setProperty("swarm.isuberjar", Boolean.TRUE.toString());
        System.setProperty("boot.module.loader", BootModuleLoader.class.getName());
        
        String mainClassName = "org.wildfly.swarm.teiid.examples.Main";
        Module module = Module.getBootModuleLoader().loadModule(ModuleIdentifier.create("swarm.application"));
        Class<?> mainClass = module.getClassLoader().loadClass(mainClassName);
        Method mainMethod = mainClass.getMethod("main", String[].class);
        mainMethod.invoke(null, new Object[]{args});
        
    }

}
