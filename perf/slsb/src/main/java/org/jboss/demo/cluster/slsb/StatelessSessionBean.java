package org.jboss.demo.cluster.slsb;

import javax.ejb.Remote;
import javax.ejb.Stateless;

@Stateless
@Remote(StatelessSession.class)
public class StatelessSessionBean implements StatelessSession {

	@SuppressWarnings("static-access")
	public void invoke(int time) {
		System.out.println("Thread " + Thread.currentThread().getName() + " invoked");
		try {
			Thread.currentThread().sleep(60000 * time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	

}
