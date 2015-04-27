package soa.group4.entities;

import soa.group4.annotation.MyElement;

public class Department extends MyXMLElement {

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

	protected String getNSURL() {
		return "http://www.nju.edu.cn/schema";
	}

	@MyElement(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@MyElement(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@MyElement(name = "webSite")
	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}
}
