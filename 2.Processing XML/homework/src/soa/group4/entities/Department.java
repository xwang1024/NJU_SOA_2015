package soa.group4.entities;

import soa.group4.annotation.MyNode;
import soa.group4.annotation.MyRootNode;

@MyRootNode(name = "department", order = { "name", "type", "webSite" }, NS = "http://www.nju.edu.cn/schema")
public class Department {
	private String name;
	private String type;
	private String webSite;

	public Department() {
	}

	public Department(String name, String type, String webSite) {
		this.name = name;
		this.type = type;
		this.webSite = webSite;
	}

	@MyNode(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@MyNode(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@MyNode(name = "webSite")
	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}
}
