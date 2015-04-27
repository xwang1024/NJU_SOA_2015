package soa.group4.entities;

import soa.group4.annotation.MyElement;

public class ScoreItem extends MyXMLElement {
	private String courseId;
	private String attendYear;
	private String attendTerm;
	private Integer classScore;
	private Integer homeworkScore;
	private Integer midTermScore;
	private Integer endTermScore;
	private Integer totalScore;
	private String comment;

	public ScoreItem() {
		super();
	}

	protected String getNSURL() {
		return "http://jw.nju.edu.cn/schema";
	}

	public ScoreItem(String courseId, String attendYear, String attendTerm, Integer classScore, Integer homeworkScore, Integer midTermScore,
			Integer endTermScore, Integer totalScore, String comment) {
		super();
		this.courseId = courseId;
		this.attendYear = attendYear;
		this.attendTerm = attendTerm;
		this.classScore = classScore;
		this.homeworkScore = homeworkScore;
		this.midTermScore = midTermScore;
		this.endTermScore = endTermScore;
		this.totalScore = totalScore;
		this.comment = comment;
	}

	@MyElement(name = "courseId")
	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	@MyElement(name = "attendYear")
	public String getAttendYear() {
		return attendYear;
	}

	public void setAttendYear(String attendYear) {
		this.attendYear = attendYear;
	}

	@MyElement(name = "attendTerm")
	public String getAttendTerm() {
		return attendTerm;
	}

	public void setAttendTerm(String attendTerm) {
		this.attendTerm = attendTerm;
	}

	@MyElement(name = "classScore")
	public Integer getClassScore() {
		return classScore;
	}

	public void setClassScore(Integer classScore) {
		this.classScore = classScore;
	}

	@MyElement(name = "homeworkScore")
	public Integer getHomeworkScore() {
		return homeworkScore;
	}

	public void setHomeworkScore(Integer homeworkScore) {
		this.homeworkScore = homeworkScore;
	}

	@MyElement(name = "midTermScore")
	public Integer getMidTermScore() {
		return midTermScore;
	}

	public void setMidTermScore(Integer midTermScore) {
		this.midTermScore = midTermScore;
	}

	@MyElement(name = "endTermScore")
	public Integer getEndTermScore() {
		return endTermScore;
	}

	public void setEndTermScore(Integer endTermScore) {
		this.endTermScore = endTermScore;
	}

	@MyElement(name = "totalScore")
	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

	@MyElement(name = "comment")
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}