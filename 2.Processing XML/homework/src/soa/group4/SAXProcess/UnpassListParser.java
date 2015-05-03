package soa.group4.SAXProcess;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import soa.group4.helper.ObjectToNodeConvertor;
import soa.group4.model.LessonScore;
import soa.group4.model.LessonScoreList;

public class UnpassListParser {
	SAXParserFactory factory;
	SAXParser parser;
	XMLReader xmlReader;
	MyErrorHandler errorHandler;
	MyContentHandler contendHandler;

	String sourceXML = "test.xml";

	public UnpassListParser() {
		errorHandler = new MyErrorHandler();
		contendHandler = new MyContentHandler();

		factory = SAXParserFactory.newInstance();
		factory.setValidating(false);
		try {
			parser = factory.newSAXParser();
			xmlReader = parser.getXMLReader();
			xmlReader.setContentHandler(contendHandler);
			xmlReader.setErrorHandler(errorHandler);
		} catch (ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		}
	}

	public void process(String systemId, Document targetDoc) throws Exception {
		try {
			xmlReader.parse(systemId);
		} catch (IOException | SAXException e) {
			e.printStackTrace();
		}
		LessonScoreList temp = this.contendHandler.getLessonScoreList();
		targetDoc.appendChild(ObjectToNodeConvertor.convert(targetDoc, temp));
	}

	// public static void main(String[] args) throws Exception {
	// /*
	// * for(LessonScore ls:lsl.getLessonScoreList()) {
	// * System.out.println(ls.getLessonID()+" "+ls.getScoreType()); for(Score
	// * s:ls.getScoreList()) {
	// * System.out.println(s.getStudentID()+" "+s.getScore()); } }
	// */
	// UnpassListParser up = new UnpassListParser();
	// DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	// DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	// Document doc = docBuilder.newDocument();
	// Element studentElement = up.process(doc);
	//
	// doc.appendChild(studentElement);
	// TransformerFactory transFactory = TransformerFactory.newInstance();
	// Transformer trans = transFactory.newTransformer();
	// DOMSource source = new DOMSource(doc);
	// StringWriter sw = new StringWriter();
	// StreamResult xmlResult = new StreamResult(sw);
	// trans.transform(source, xmlResult);
	// System.out.println(sw.toString());
	// }

}
