package soa.group4.model;

import java.util.List;

import soa.group4.annotation.MyNode;
import soa.group4.annotation.MyList;
import soa.group4.annotation.MyRootNode;

@MyRootNode(name = "student", order = { "personInfo", "major", "grade", "class", "scoreList" }, NS = "http://jw.nju.edu.cn/schema")
public class Student {
	private PersonInfo personInfo;
	private String major;
	private String grade;
	private String class_;
	private List<ScoreItem> scoreList;

	public Student() {
		super();
	}

	public Student(PersonInfo personInfo, String major, String grade, String class_, List<ScoreItem> scoreList) {
		super();
		this.personInfo = personInfo;
		this.major = major;
		this.grade = grade;
		this.class_ = class_;
		this.scoreList = scoreList;
	}

	@MyNode(name = "personInfo", isSimple = false)
	public PersonInfo getPersonInfo() {
		return personInfo;
	}

	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}

	@MyNode(name = "major")
	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	@MyNode(name = "grade")
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@MyNode(name = "class")
	public String getClass_() {
		return class_;
	}

	public void setClass_(String class_) {
		this.class_ = class_;
	}

	@MyList(name = "scoreList", itemName = "scoreItem")
	public List<ScoreItem> getScoreList() {
		return scoreList;
	}

	public void setScoreList(List<ScoreItem> scoreList) {
		this.scoreList = scoreList;
	}
}
