package org.wildfly.wfly2920;

import java.rmi.RemoteException;

import javax.ejb.EJBHome;

public interface InvoiceManagerEJBHome extends EJBHome {

    public InvoiceManagerEJB create() throws RemoteException;

}
