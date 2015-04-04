package org.jboss.staxmapper.quickstart;

/**
 * 
 * @author kylin
 *
 */
public class Employee {
	
	private String id;

	private String name;
	
	private String age;
	
	private String gender;
	
	private String role ;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", age=" + age
				+ ", gender=" + gender + ", role=" + role + "]";
	}

	
}
