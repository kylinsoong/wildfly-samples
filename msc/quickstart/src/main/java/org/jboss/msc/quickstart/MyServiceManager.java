package org.jboss.msc.quickstart;

public class MyServiceManager {
	
	private String description;
	
	public void initialize(String description) {
        this.description = description;
    }
	
	public void cleanup() {
        this.description = null;
    }

	public String getDescription() {
		return description;
	}

}
