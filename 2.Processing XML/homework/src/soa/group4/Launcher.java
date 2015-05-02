package soa.group4;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import soa.group4.SAXProcess.UnpassListParser;
import soa.group4.helper.DocumentHelper;
import soa.group4.helper.ObjectToNodeConvertor;
import soa.group4.model.Department;
import soa.group4.model.PersonInfo;
import soa.group4.model.ScoreItem;
import soa.group4.model.Student;

public class Launcher {

	/**
	 * 生成所有课程的成绩列表
	 * 
	 * @param courseIds
	 * @return
	 */
	private static ArrayList<ScoreItem> generateRandomCourses(String[] courseIds) {
		ArrayList<ScoreItem> items = new ArrayList<ScoreItem>();
		for (int i = 0; i < courseIds.length; i++) {
			ScoreItem item = new ScoreItem(courseIds[i], 2012 + i / 2 + "", 1 + i % 2 + "", generate60plusScore(), generate60plusScore(),
					generate60plusScore(), generate60plusScore(), 0, "");
			autoSetTotalScore(item);
			items.add(item);
		}
		return items;
	}

	/**
	 * 根据平时成绩计算总分
	 * 
	 * @param item
	 */
	private static void autoSetTotalScore(ScoreItem item) {
		double total = item.getClassScore() * 0.1 + item.getHomeworkScore() * 0.2 + item.getMidTermScore() * 0.3 + item.getEndTermScore() * 0.4;
		item.setTotalScore((int) total);
	}

	/**
	 * 生成大于60的分数
	 * 
	 * @return
	 */
	private static int generate60plusScore() {
		return (int) (60 + Math.random() * 40);
	}

	public static void main(String[] args) throws Exception {
		DocumentHelper helper = new DocumentHelper();
		helper.initByCreate(false);
		Document doc = helper.getDocument();
		Element listElement = doc.createElementNS("http://jw.nju.edu.cn/schema", "studentList");

		Department department = new Department("软件学院", "院系", "http://software.nju.edu.cn");
		// 生成课程号
		String[] courseIds = new String[5];
		for (int i = 0; i < courseIds.length; i++) {
			courseIds[i] = String.format("%6d", i + 1).replace(" ", "0");
		}
		// 小组成员信息
		PersonInfo[] personInfos = { new PersonInfo("121250040", "郭云龙", "男", "1995-03-28", department),
				new PersonInfo("121250051", "黄嘉伟", "男", "1993-09-27", department), new PersonInfo("121250080", "刘寒啸", "男", "1995-01-23", department),
				new PersonInfo("121250157", "王鑫", "男", "1993-12-20", department), new PersonInfo("121250218", "赵立攀", "男", "1993-11-22", department), };

		// 不及格的同学的下标
		int luckyIndex = (int) (Math.random() * personInfos.length);
		// 向studentList
		for (int i = 0; i < personInfos.length; i++) {
			ArrayList<ScoreItem> scoreList = generateRandomCourses(courseIds);
			if (i == luckyIndex) {
				ScoreItem item = scoreList.get(scoreList.size() - 1);
				item.setEndTermScore(0);
				autoSetTotalScore(item);
			}
			String numIdStr = personInfos[i].getId().replaceAll("\\D", "");
			String grade = "20" + numIdStr.substring(0, 2);
			int class_ = Integer.parseInt(numIdStr.substring(numIdStr.length() - 3)) / 60 + 1;
			Student student = new Student(personInfos[i], "软件工程", grade, class_ + "", scoreList);
			Element studentElement = ObjectToNodeConvertor.convert(doc, student);
			listElement.appendChild(studentElement);
		}
		doc.appendChild(listElement);
		if (helper.validate("studentList.xsd")) {
			helper.save("studentList.xml");
		}

		Document doc2 = helper.transform("studentList2scoreList.xsl", false);
		DocumentHelper helper2 = new DocumentHelper(doc2);
		helper2.save("scoreList.xml");

		UnpassListParser upl = new UnpassListParser();
		upl.changeSource("scoreList.xml");
		DocumentHelper helper3 = new DocumentHelper();
		helper3.initByCreate(false);
		Document doc3 = helper3.getDocument();
		upl.process(doc3);
		helper3.save("scoreList.xml");
	}
}
