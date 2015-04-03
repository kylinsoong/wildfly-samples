package org.jboss.dmr;

public class testBase64 {

	public static void main(String[] args) {
		
		String encode = Base64.encodeBytes("Kylin Soong".getBytes());
		
		System.out.println(encode);
		
		String decode = new String(Base64.decode(encode));
		
		System.out.println(decode);
	}

}
