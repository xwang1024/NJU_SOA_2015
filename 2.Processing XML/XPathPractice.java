import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * XPath的练习
 * 
 * @author xwang1024
 *
 */
public class XPathPractice {
	static String[] testExprSet = { "/studentList",
		"/studentList/student",
		"/studentList/student/@schoolId", 
		"//name",
		"/studentList//name",
		"/studentList/student[1]",
		"/studentList/student[last()]",
		"/studentList/student[last()-1]",
		"/studentList/student[position()=2]",
		"/studentList/student[position()<2]",
		"/studentList/student[@schoolId]",
		"/studentList/student[@schoolId='121250003']",
		"/studentList/student[birthday='1997-06-09']/name",
		"/studentList/*/name",
		"/studentList/student/@*",
		"/studentList/student/node()",
		"/studentList/student[@*]",
		"//*",
		"/studentList/student/attribute::schoolId",
		"/studentList/student/name/text()",
		"/studentList/student[string()]",	
		"/studentList/student[contains(name,'妮可')]",
		"/studentList/student[contains(name,'西')]",
		"/studentList/student[substring-after(name,'木')]",
		};

	public static void main(String[] args) throws Exception {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		docBuilderFactory.setValidating(false);
		docBuilderFactory.setNamespaceAware(false);
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document doc = docBuilder.parse("studentList.xml");

		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath;
		String expression;
		NodeList list;
		xpath = xpathFactory.newXPath();
		for (int i = 0; i < testExprSet.length; i++) {
			expression = testExprSet[i];
			list = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);
			printNodeList(list);
		}
	}

	public static void printNodeList(NodeList list) {
		int len;
		if ((len = list.getLength()) == 0) {
			System.out.println("Empty List");
		}
		for (int i = 0; i < len; i++) {
			System.out.print(list.item(i).getNodeName());
			System.out.print(" = ");
			System.out.print(list.item(i).getNodeValue());
			System.out.print(" : ");
			System.out.println(list.item(i).getTextContent());
		}
		System.out.println("==============================");
	}
}
