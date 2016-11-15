package server;

import org.jboss.modules.ModuleLoadException;

public class ClassLoadException extends RuntimeException {

    private static final long serialVersionUID = 9112925168916649500L;
    
    public ClassLoadException(ModuleLoadException e) {
        super(e);
    }

}
