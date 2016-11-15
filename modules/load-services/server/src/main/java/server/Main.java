package server;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Iterator;
import java.util.ServiceLoader;

import org.jboss.modules.Module;
import org.jboss.modules.ModuleIdentifier;
import org.jboss.modules.ModuleLoadException;

import api.HelloWorld;

public class Main {

    public static void main(String[] args) {
        HelloWorld service = buildService(HelloWorld.class, "impl");
        System.out.println(service.sayHello());
        System.out.println(service.getTimestamp());
    }

    static <T> T buildService(Class<T> type, String moduleName) {
        final ModuleIdentifier moduleId;
        final Module module;
        try {
            moduleId = ModuleIdentifier.create(moduleName);
            module = Module.getBootModuleLoader().loadModule(moduleId); //If use Module.getCallerModuleLoader(), need set class loader as below method
        } catch (ModuleLoadException e) {
            throw new ClassLoadException(e);
        }
        ServiceLoader<T> services = module.loadService(type);
        Iterator<T> iter = services.iterator();
        if (!iter.hasNext()){
            throw new ServiceNotFoundException("Can not load " + type + " from " + moduleName);
        }
        return iter.next();
    }
    
    @SuppressWarnings("unchecked")
    static <T> T buildService(Class<T> type, String moduleName, boolean isSetClassLoader) {
        final T instance = buildService(type, moduleName);
        if(isSetClassLoader) {
            ClassLoader loader = instance.getClass().getClassLoader();
            T proxy = (T) Proxy.newProxyInstance(loader, new Class[]{type}, new InvocationHandler(){

                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    ClassLoader originalCL = Thread.currentThread().getContextClassLoader();
                    Thread.currentThread().setContextClassLoader(loader);
                    try {
                        return method.invoke(instance, args);
                    } finally {
                        Thread.currentThread().setContextClassLoader(originalCL);
                    }
                }});
            return proxy;
        } else {
            return instance;
        }
    }

}
