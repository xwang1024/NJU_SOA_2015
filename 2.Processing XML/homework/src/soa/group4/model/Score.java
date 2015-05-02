package soa.group4.model;

import soa.group4.annotation.MyNode;
import soa.group4.annotation.MyRootNode;

@MyRootNode(name = "成绩", NS = "http://www.nju.edu.cn/schema", order = { "学号", "得分" })
public class Score {
	String studentID;
	int score;

	public Score() {
		super();
		this.studentID = new String();
	}

	@MyNode(name = "学号")
	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	@MyNode(name = "得分")
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
