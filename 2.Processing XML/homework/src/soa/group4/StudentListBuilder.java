package soa.group4;

import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import soa.group4.entities.Department;
import soa.group4.entities.PersonInfo;
import soa.group4.entities.ScoreItem;
import soa.group4.entities.Student;

public class StudentListBuilder {
	public static void main(String[] args) throws Exception {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		docFactory.setNamespaceAware(false);
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(new File("studentList.xsd"));
		docFactory.setSchema(schema);

		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();

		Department department = new Department("软件学院", "院系", "http://software.nju.edu.cn");
		PersonInfo personInfo = new PersonInfo("121250040", "陈云龙", "男", "1995-03-28", department);
		ArrayList<ScoreItem> scoreList = new ArrayList<ScoreItem>();
		scoreList.add(new ScoreItem("000001", "2012", "1", 95, 95, 95, 95, 95, ""));
		scoreList.add(new ScoreItem("000002", "2012", "1", 95, 95, 95, 95, 95, ""));
		Student student = new Student(personInfo, "软件工程", "2012", "1", scoreList);
		Element studentElement = student.toXMLElement(doc, "student");
		Element listElement = doc.createElementNS("http://jw.nju.edu.cn/schema", "studentList");
		listElement.appendChild(studentElement);
		listElement.appendChild(studentElement);
//		listElement.setAttribute("xmlns:school", "http://www.nju.edu.cn/schema");
//		listElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
//		listElement.setAttribute("xsi:schemaLocation", "http://jw.nju.edu.cn/schema studentList.xsd");
		doc.appendChild(listElement);
		schema.newValidator().validate(new DOMSource(doc));
		TransformerFactory transFactory = TransformerFactory.newInstance();
		Transformer trans = transFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StringWriter sw = new StringWriter();
		StreamResult xmlResult = new StreamResult(sw);
		trans.transform(source, xmlResult);
		System.out.println(sw.toString());
	}
}
