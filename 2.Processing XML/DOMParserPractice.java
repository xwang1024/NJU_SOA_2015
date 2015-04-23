import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * 描述了java内置的DOM Parser的使用方式，如果不开心可以使用其它开源包来虐杀xml
 * 感觉XML各种严格有点受不了
 * 
 * @author xwang1024
 *
 */
public class DOMParserPractice {
	public static Document createExample() throws Exception {
		// 新建document构造器的工厂实例
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		// 是否在读取过程中对xml进行 DTD 验证
		docFactory.setValidating(false);
		// 使用工厂构建document构造器，如果对工厂配置有误则会抛出异常
		DocumentBuilder builder = docFactory.newDocumentBuilder();
		// 创建一个新的document
		Document doc = builder.newDocument();
		// xml的版本
		doc.setXmlVersion("1.0");
		// Standalone = false 表示这个xml文档不是独立的而是依赖于外部所定义的一个DTD
		// Standalone = true 表示这个xml文档是自包含的(self-contained)
		doc.setXmlStandalone(false);
		// 创建元素，并加上xmlns属性
		Element student = doc.createElementNS("http://jw.nju.edu.cn/student", "student");
		// 创建属性
		Attr attr = doc.createAttribute("schoolId");
		// 设置属性值
		attr.setValue("121250001");
		// 给元素加上属性
		student.setAttributeNode(attr);
		// 给元素设置属性的另一种方法
		student.setAttribute("gender", "女");
		// 创建元素
		Element name = doc.createElement("name");
		// 设置元素的文本内容
		name.setTextContent("矢泽妮可");
		// 将name元素加入student元素
		student.appendChild(name);
		// 创建元素
		Element birthday = doc.createElement("birthday");
		// 设置元素的文本内容
		birthday.setTextContent("1997-07-22");
		// 将birthday元素加入student元素
		student.appendChild(birthday);
		// 要想再插入一遍则必须clone（此处不符合schema）
		// student.appendChild(birthday.cloneNode(true));
		// 将student元素加入根元素
		doc.appendChild(student);
		return doc;
	}

	public static Document updateExample() throws Exception {
		// 新建document构造器的工厂实例
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		// 新建schema工厂实例，参数一般为XMLConstants.W3C_XML_SCHEMA_NS_URI(即"http://www.w3.org/2001/XMLSchema")
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		// 读取schema文件
		Schema schema = schemaFactory.newSchema(new File("student.xsd"));
		// document构造器工厂加载schema
		docFactory.setSchema(schema);
		// 是否在读取过程中对xml进行 DTD 验证
		docFactory.setValidating(false);
		docFactory.setNamespaceAware(true);
		// 使用工厂构建document构造器，如果对工厂配置有误则会抛出异常
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		// 可以设置错误处理器
		docBuilder.setErrorHandler(new ErrorHandler() {
			@Override
			public void warning(SAXParseException exception) throws SAXException {
				System.err.println("Warning: ".concat(exception.getMessage()));
			}

			@Override
			public void fatalError(SAXParseException exception) throws SAXException {
				System.err.println("FatalError: ".concat(exception.getMessage()));
			}

			@Override
			public void error(SAXParseException exception) throws SAXException {
				System.err.println("Error: ".concat(exception.getMessage()));
			}
		});
		// 将外部文件转换为document
		Document doc = docBuilder.parse(new File("student.xml"));
		// XPath 工厂
		XPathFactory xpathFactory = XPathFactory.newInstance();
		// XPath 实例
		XPath xpath = xpathFactory.newXPath();
		// 因为前面NamespaceAware设为了true，所以这里必须有namespace的上下文
		xpath.setNamespaceContext(new NamespaceContext() {
			@Override
			public Iterator getPrefixes(String namespaceURI) {
				// Not need
				return null;
			}
			@Override
			public String getPrefix(String namespaceURI) {
				// Not need
				return null;
			}
			@Override
			public String getNamespaceURI(String prefix) {
				if(prefix.equals("jw")) {
					return "http://jw.nju.edu.cn/student";
				}
				return null;
			}
		});
		// XPath需要带前缀
		Node node = (Node) xpath.evaluate("/jw:student/jw:name", doc, XPathConstants.NODE);
		// 修改name的文本内容
		node.setTextContent("西木野真姬");
		return doc;
	}

	public static boolean validateExample(Document doc, Schema schema) throws Exception {
		// doc进行验证，如果验证不过则会抛异常！
		try {
			schema.newValidator().validate(new DOMSource(doc));
		} catch (SAXException e) {
			System.err.println(e.getMessage());
			return false;
		} catch (IOException e) {
			throw e;
		}
		return true;
	}

	public static void outputExample(Document doc) throws Exception {
		// 新建转换器工厂实例
		TransformerFactory transFactory = TransformerFactory.newInstance();
		// 新建转换器（用于输出）
		Transformer trans = transFactory.newTransformer();
		// 输出配制（编码）
		trans.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		// 将dom树转换为source树
		DOMSource domSource = new DOMSource(doc);
		// 新建StringWriter作为输出位置
		StringWriter sw = new StringWriter();
		// 把StringWriter作为result的输出
		StreamResult xmlResult = new StreamResult(sw);
		// 将source转换为result
		trans.transform(domSource, xmlResult);
		// 打印输出
		System.out.println(sw.toString());
	}

	public static void main(String[] args) throws Exception {
		Document doc = createExample();
		outputExample(doc);
		doc = updateExample();
		outputExample(doc);
	}
}
