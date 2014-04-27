package org.wildfly.perf.client;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;


import org.wildfly.perf.slsb.StatelessSession;

public class StatelessSessionBeanClient {
	
	private String applicationContext = "wildfly-perf-slsb";
	private String SLSB_JNDI = "ejb:/" + applicationContext + "/StatelessSessionBean!" + StatelessSession.class.getName() ;		
	
	protected void execute() throws Exception {
		Hashtable<String, String> jndiProps = new Hashtable<String, String>();
		jndiProps.put( Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming" );
		Context context = new InitialContext( jndiProps );
		final StatelessSession slsb = (StatelessSession)context.lookup(SLSB_JNDI);
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				public void run() {
					System.out.println(Thread.currentThread().getName() + " Started");
					slsb.singletonInvoke();
				}
			}).start();
		}
	}


	public static void main(String[] args) throws Exception {
		new StatelessSessionBeanClient().execute();
	}
}
