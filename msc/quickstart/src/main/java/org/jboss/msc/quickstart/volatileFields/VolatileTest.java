package org.jboss.msc.quickstart.volatileFields;

/*
 * java -cp target/msc-quickstart.jar org.jboss.msc.quickstart.volatileFields.VolatileTest
 */
public class VolatileTest {

	public static void main(String[] args) {
		
		new Thread(new Runnable(){

			public void run() {
				while(true) {
					Test3.two();
				}
			}}).start();

		new Thread(new Runnable(){

			public void run() {
				while(true) {
					Test3.one();
				}
			}}).start();
	}

}
