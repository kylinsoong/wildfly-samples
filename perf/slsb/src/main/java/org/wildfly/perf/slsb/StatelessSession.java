package org.wildfly.perf.slsb;

public interface StatelessSession {
	public void invoke(int time);
	public void singletonInvoke();
}
