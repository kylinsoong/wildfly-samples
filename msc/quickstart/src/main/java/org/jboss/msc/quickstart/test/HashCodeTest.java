package org.jboss.msc.quickstart.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


public class HashCodeTest {

	public static void main(String[] args) {
		new HashCodeTest().test();
	}
	
	private void test() {
		
		Set set = new HashSet();
		
		String str = "1";
		set.add(str);
		set.add(new String(str));
		set.add(new String("1"));
		set.add(new A("1"));
		set.add(new A("1"));
		System.out.println(set.size());
		
//		Entity a = new Entity("test1");
//		Entity b = new Entity("test2");
//		List<Entity> list  = new ArrayList<Entity>();
//		list.add(b);
//		list.add(a);
//		System.out.println(list);
//		Collections.sort(list);
//		System.out.println(list);
//		System.out.println(a.equals(b));
		
//		Set<Entity> set = new HashSet<Entity>();
//		set.add(a);
//		System.out.println();
//		set.add(b);
//		System.out.println();
//		System.out.println(set.size());
	}
	
	class A {
		String s;

		public A(String s) {
			super();
			this.s = s;
		}
		
		public int hashCode() {
			return s.hashCode();
		}
		
		public boolean equals(Object obj) {
			return this == obj || obj instanceof A && ((A)obj).hashCode() == hashCode();
		}
		
	}

	class Entity implements Comparable<Entity> {
		
		String name;

		public Entity(String name) {
			super();
			this.name = name;
		}

		public int hashCode() {
			System.out.println(name + " hashCode");
			return 31 * name.hashCode();
		}

		public boolean equals(Object obj) {
			System.out.println("equals");
//			return this == obj || obj instanceof Entity && ((Entity)obj).hashCode() == hashCode();
			return false;
		}

		@Override
		public String toString() {
			return "Entity [name=" + name + "]";
		}

		public int compareTo(Entity o) {
			System.out.println("compareTo");
			
			if(this == o) {
				return 0;
			}
			
			return hashCode() - o.hashCode() ;
		}
	}

}
