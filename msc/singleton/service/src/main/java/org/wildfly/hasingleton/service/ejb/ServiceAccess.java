package org.wildfly.hasingleton.service.ejb;

import javax.ejb.Remote;

@Remote
public interface ServiceAccess {
	public abstract String getNodeNameOfService();
}
