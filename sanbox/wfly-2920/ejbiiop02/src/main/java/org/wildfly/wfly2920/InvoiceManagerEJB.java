package org.wildfly.wfly2920;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

public interface InvoiceManagerEJB extends EJBObject {

    public void createInvoice(String name) throws RemoteException;
}
