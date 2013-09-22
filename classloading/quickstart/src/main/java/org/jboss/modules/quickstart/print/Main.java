package org.jboss.modules.quickstart.print;

public class Main {
	

	public static void main(String[] args) {
		
		System.out.printf("\n    Modular Class Loading Print Class Loader Demo \n\n");
	
		
		new Main().printClassLoader() ;
	}
	
	private void printClassLoader() {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		System.out.println("Print Class Loader Hierarchy:");
		print(loader);
	}
	
	private void print(ClassLoader loader) {
		println("  " + loader);
		if(loader.getParent() != null) {
			print(loader.getParent());
		}
	}
	
	private void println(Object obj) {
		System.out.println(obj);
	}

}
