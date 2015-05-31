package soa.group4;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import soa.group4.model.Student;

/**
 * Servlet implementation class GetStudent
 */
@WebServlet("/GetStudent")
public class GetStudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetStudentServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestId = request.getParameter("id");
		OutputStream out = response.getOutputStream();
		if (requestId == null) {
			try {
				responseSOAPFault(new MyFault("0x1111", "You must post id.", "server"), out);
			} catch (SOAPException e) {
				e.printStackTrace();
			}
			out.flush();
			out.close();
			return;
		}
		System.out.println(requestId);
		try {
			Student student = findStudent(requestId);
			if(student == null) {
				responseSOAPFault(new MyFault("0x1110", "Can't find this student.", "server"), out);
			} else {
				responseSOAPMessage(student, out);
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				responseSOAPFault(new MyFault("0x1101", "Processing error: " + e.getMessage(), "server"), out);
			} catch (SOAPException e1) {
				e1.printStackTrace();
			}
		}

	}

	private Student findStudent(String id) throws Exception {
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(this.getClass().getResourceAsStream("studentList.xml"));
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expr = "/studentList/student/personInfo[id=\'" + id + "\']/..";
		Node studentNode = (Node) xpath.evaluate(expr, doc, XPathConstants.NODE);
		if (studentNode == null) {
			System.out.println("null");
			return null;
		}
		studentNode = studentNode.cloneNode(true);
		System.out.println(studentNode.getTextContent());
		Student student = new Student();
		String expr2;
		String value;
		expr2 = "//birthday";
		value = ((Node) xpath.evaluate(expr2, studentNode, XPathConstants.NODE)).getTextContent();
		student.setBirthday(value);
		System.out.println(student.getBirthday());
		expr2 = "//class";
		value = ((Node) xpath.evaluate(expr2, studentNode, XPathConstants.NODE)).getTextContent().trim();
		student.setClass_(value);
		expr2 = "//department";
		value = ((Node) xpath.evaluate(expr2, studentNode, XPathConstants.NODE)).getChildNodes().item(0).getTextContent().trim();
		student.setDepartment(value);
		expr2 = "//gender";
		value = ((Node) xpath.evaluate(expr2, studentNode, XPathConstants.NODE)).getTextContent().trim();
		student.setGender(value);
		expr2 = "//grade";
		value = ((Node) xpath.evaluate(expr2, studentNode, XPathConstants.NODE)).getTextContent().trim();
		student.setGrade(value);
		expr2 = "//id";
		value = ((Node) xpath.evaluate(expr2, studentNode, XPathConstants.NODE)).getTextContent().trim();
		student.setId(value);
		expr2 = "//major";
		value = ((Node) xpath.evaluate(expr2, studentNode, XPathConstants.NODE)).getTextContent().trim();
		student.setMajor(value);
		expr2 = "//name";
		value = ((Node) xpath.evaluate(expr2, studentNode, XPathConstants.NODE)).getTextContent().trim();
		student.setName(value);
		System.out.println(student);
		return student;
	}

	private void responseSOAPMessage(Student student, OutputStream out) throws SOAPException, IOException {
		MessageFactory factory = MessageFactory.newInstance();
		SOAPMessage message = factory.createMessage();
		SOAPPart part = message.getSOAPPart();
		SOAPEnvelope envelope = part.getEnvelope();
		SOAPBody body = envelope.getBody();
		QName studentQname = new QName("http://jw.nju.edu.cn/schema", "student", "jw");
		SOAPBodyElement ele = body.addBodyElement(studentQname);
		ele.addChildElement("id", "jw").setTextContent(student.getId());
		ele.addChildElement("name", "jw").setTextContent(student.getName());
		ele.addChildElement("gender", "jw").setTextContent(student.getGender());
		ele.addChildElement("birthday", "jw").setTextContent(student.getBirthday());
		QName deptQname = new QName("http://www.nju.edu.cn/schema", "department", "school");
		ele.addChildElement(deptQname).addChildElement("name", "school").setTextContent(student.getDepartment());
		ele.addChildElement("major", "jw").setTextContent(student.getMajor());
		ele.addChildElement("grade", "jw").setTextContent(student.getGrade());
		ele.addChildElement("class", "jw").setTextContent(student.getClass_());
		message.writeTo(out);
	}
	private void responseSOAPFault(MyFault myFault, OutputStream out) throws SOAPException, IOException {
		MessageFactory factory = MessageFactory.newInstance();
		SOAPMessage message = factory.createMessage();
		SOAPPart part = message.getSOAPPart();
		SOAPEnvelope envelope = part.getEnvelope();
		SOAPBody body = envelope.getBody();
		SOAPFault fault = body.addFault();
		fault.setFaultCode(myFault.code);
		fault.setFaultString(myFault.message);
		fault.setFaultActor(myFault.actor);
		message.writeTo(out);
	}
	
	private static class MyFault {
		String code;
		String message;
		String actor;
		
		public MyFault(String code, String message, String actor) {
			super();
			this.code = code;
			this.message = message;
			this.actor = actor;
		}
		
		
	}
}
