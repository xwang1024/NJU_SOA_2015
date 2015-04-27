package soa.group4.entities;

import soa.group4.annotation.MyElement;

public class PersonInfo extends MyXMLElement {
	private String id;
	private String name;
	private String gender;
	private String birthday;
	private Department department;

	public PersonInfo() {
		super();
	}

	protected String getNSURL() {
		return "http://www.nju.edu.cn/schema";
	}

	public PersonInfo(String id, String name, String gender, String birthday, Department department) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.birthday = birthday;
		this.department = department;
	}

	@MyElement(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@MyElement(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@MyElement(name = "gender")
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@MyElement(name = "birthday")
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	@MyElement(name = "department", isSimple = false)
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

}
