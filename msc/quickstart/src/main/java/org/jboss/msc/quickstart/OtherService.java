package org.jboss.msc.quickstart;

import org.jboss.msc.service.Service;
import org.jboss.msc.service.StartContext;
import org.jboss.msc.service.StartException;
import org.jboss.msc.service.StopContext;

public class OtherService implements Service<OtherService> {

	public OtherService getValue() throws IllegalStateException, IllegalArgumentException {
		return null;
	}

	public void start(StartContext context) throws StartException {

	}

	public void stop(StopContext context) {

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
