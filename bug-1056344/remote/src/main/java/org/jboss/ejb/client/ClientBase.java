package org.jboss.ejb.client;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public abstract class ClientBase {

	protected Context getContext() throws NamingException {
		Hashtable<String, String> jndiProperties = new Hashtable<String, String>();
		jndiProperties.put(Context.URL_PKG_PREFIXES,"org.jboss.ejb.client.naming");
		Context context = new InitialContext(jndiProperties);

		return context;
	}

	protected void prompt(Object msg) {
		System.out.println("\n  " + msg);
	}

	protected void stop(Long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public abstract void test() throws Exception;

}