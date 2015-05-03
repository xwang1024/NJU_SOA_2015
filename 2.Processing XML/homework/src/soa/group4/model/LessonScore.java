package soa.group4.model;

import java.util.ArrayList;
import java.util.List;

import soa.group4.annotation.MyList;
import soa.group4.annotation.MyNode;
import soa.group4.annotation.MyRootNode;

@MyRootNode(name = "课程成绩", NS = "http://jw.nju.edu.cn/schema", order = { "课程编号", "成绩性质", "课程成绩列表" })
public class LessonScore {
	String lessonID;
	String scoreType;
	List<Score> scoreList;

	public LessonScore() {
		super();
		this.lessonID = new String();
		this.scoreType = new String();
		this.scoreList = new ArrayList<Score>();
	}

	@MyNode(name = "课程编号", type = MyNode.Type.attribute)
	public String getLessonID() {
		return lessonID;
	}

	public void setLessonID(String lessonID) {
		this.lessonID = lessonID;
	}

	@MyNode(name = "成绩性质", type = MyNode.Type.attribute)
	public String getScoreType() {
		return scoreType;
	}

	public void setScoreType(String scoreType) {
		this.scoreType = scoreType;
	}

	@MyList(name = "课程成绩列表", itemName = "成绩", needWrap = false)
	public List<Score> getScoreList() {
		return scoreList;
	}

	public void setScoreList(List<Score> scoreList) {
		this.scoreList = scoreList;
	}
}
