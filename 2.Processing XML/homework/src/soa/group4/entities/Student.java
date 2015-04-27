package soa.group4.entities;

import java.util.List;

import soa.group4.annotation.MyElement;
import soa.group4.annotation.MyList;

public class Student extends MyXMLElement {
	private PersonInfo personInfo;
	private String major;
	private String grade;
	private String class_;
	private List<ScoreItem> scoreList;

	public Student() {
		super();
	}

	protected String getNSURL() {
		return "http://jw.nju.edu.cn/schema";
	}

	public Student(PersonInfo personInfo, String major, String grade, String class_, List<ScoreItem> scoreList) {
		super();
		this.personInfo = personInfo;
		this.major = major;
		this.grade = grade;
		this.class_ = class_;
		this.scoreList = scoreList;
	}

	@MyElement(name = "personInfo", isSimple = false)
	public PersonInfo getPersonInfo() {
		return personInfo;
	}

	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}

	@MyElement(name = "major")
	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	@MyElement(name = "grade")
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@MyElement(name = "class")
	public String getClass_() {
		return class_;
	}

	public void setClass_(String class_) {
		this.class_ = class_;
	}

	@MyList(name = "scoreList", itemName = "scoreItem", isItemSimple = false)
	public List<ScoreItem> getScoreList() {
		return scoreList;
	}

	public void setScoreList(List<ScoreItem> scoreList) {
		this.scoreList = scoreList;
	}
}
