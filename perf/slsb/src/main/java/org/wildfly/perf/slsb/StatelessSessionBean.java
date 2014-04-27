package org.wildfly.perf.slsb;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.wildfly.perf.concurrency.KylinSoong;

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

	public void singletonInvoke() {
		KylinSoong.getInstance().foo();
	}

	

}
