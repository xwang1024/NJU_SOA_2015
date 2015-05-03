package soa.group4.SAXProcess;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import soa.group4.model.LessonScore;
import soa.group4.model.LessonScoreList;
import soa.group4.model.Score;

public class MyContentHandler extends DefaultHandler {
	final private int passScore = 60;

	private String status;

	private Score score;
	private LessonScore lessonScore;
	private LessonScoreList lessonScoreList;

	// List<Student> list = new ArrayList<Student>();

	public LessonScoreList getLessonScoreList() {
		return this.lessonScoreList;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		String text = new String(ch, start, length).trim();
		if (!text.equals("")) {
			switch (status) {
			case "课程成绩列表":
				break;
			case "课程成绩":
				break;
			case "成绩":
				break;
			case "学号":
				score.setStudentID(text);
				break;
			case "得分":
				score.setScore(Integer.valueOf(text));
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void endDocument() throws SAXException {
		super.endDocument();

	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
		switch (qName) {
		case "课程成绩列表":
			break;
		case "课程成绩":
			if (lessonScore.getScoreList().size() > 0) {
				lessonScoreList.getLessonScoreList().add(lessonScore);
			}
			break;
		case "成绩":
			if (score.getScore() < this.passScore) {
				lessonScore.getScoreList().add(score);
			}
			break;
		case "学号":
			break;
		case "得分":
			break;
		default:
			break;
		}
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		this.status = qName;

		switch (qName) {
		case "课程成绩列表":
			lessonScoreList = new LessonScoreList();
			break;
		case "课程成绩":
			lessonScore = new LessonScore();
			for (int i = 0; i < attributes.getLength(); i++) {
				// 成绩性质
				if (attributes.getQName(i).equals("课程编号")) {
					lessonScore.setLessonID(attributes.getValue(i));
				}

				if (attributes.getQName(i).equals("成绩性质")) {
					lessonScore.setScoreType(attributes.getValue(i));
				}
			}
			break;
		case "成绩":
			score = new Score();
			break;
		case "学号":
			break;
		case "得分":
			break;
		default:
			break;
		}
	}
}