package org.wildfly.hasingleton.service.ejb.client;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.wildfly.hasingleton.service.ejb.ServiceAccess;


public class SingletonServiceClient {
   
    private final ServiceAccess accessBean;

  
    private SingletonServiceClient() throws NamingException {
        final Hashtable<String, String> jndiProperties = new Hashtable<String, String>();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        final Context context = new InitialContext(jndiProperties);
        String lookupName = "ejb:/wildfly-ha-singleton-service/ServiceAccessBean!" + ServiceAccess.class.getName();
        System.out.println("Lookup Bean name is " + lookupName);
        accessBean = (ServiceAccess) context.lookup(lookupName);
    }

    private String getServiceNodeName() {
        return accessBean.getNodeNameOfService();
    }


    public static void main(String[] args) throws NamingException {
        SingletonServiceClient client = new SingletonServiceClient();

        for (int i = 0; i < 4; i++) {
            System.out.println("#" + i + " The service is active on node with name = " + client.getServiceNodeName());
        }

    }

}
