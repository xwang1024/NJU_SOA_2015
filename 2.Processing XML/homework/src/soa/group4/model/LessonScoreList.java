package soa.group4.model;

import java.util.ArrayList;
import java.util.List;

import soa.group4.annotation.MyList;
import soa.group4.annotation.MyRootNode;

@MyRootNode(name = "课程成绩列表", NS = "http://jw.nju.edu.cn/schema", order = { "课程成绩列表" })
public class LessonScoreList {
	private List<LessonScore> lessonScoreList;

	public LessonScoreList() {
		this.lessonScoreList = new ArrayList<LessonScore>();
	}

	@MyList(name = "课程成绩列表", itemName = "课程成绩", needWrap = false)
	public List<LessonScore> getLessonScoreList() {
		return lessonScoreList;
	}

	public void setLessonScoreList(List<LessonScore> lessonScoreList) {
		this.lessonScoreList = lessonScoreList;
	}
}
