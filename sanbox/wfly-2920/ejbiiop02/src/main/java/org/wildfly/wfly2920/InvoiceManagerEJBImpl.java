package org.wildfly.wfly2920;

import javax.ejb.RemoteHome;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


@RemoteHome(InvoiceManagerEJBHome.class)
@Stateless
public class InvoiceManagerEJBImpl {
    
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void createInvoice(String name)  {

    }
}
