package soa.group4.model;

import soa.group4.annotation.MyNode;
import soa.group4.annotation.MyRootNode;

@MyRootNode(name = "personInfo", order = { "id", "name", "gender", "birthday", "department" }, NS = "http://www.nju.edu.cn/schema")
public class PersonInfo {
	private String id;
	private String name;
	private String gender;
	private String birthday;
	private Department department;

	public PersonInfo() {
		super();
	}

	public PersonInfo(String id, String name, String gender, String birthday, Department department) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.birthday = birthday;
		this.department = department;
	}

	@MyNode(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@MyNode(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@MyNode(name = "gender")
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@MyNode(name = "birthday")
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	@MyNode(name = "department", isSimple = false)
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

}
