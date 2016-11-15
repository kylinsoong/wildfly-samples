package server;

public class ServiceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -735740667841521896L;
    
    public ServiceNotFoundException(String msg) {
        super(msg);
    }

}
