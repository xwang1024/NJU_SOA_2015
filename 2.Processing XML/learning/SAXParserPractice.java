import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

/**
 * 描述了java内置的SAX Parser的使用方式，为了效率增加了人的工作量。。。
 * 
 * @author xwang1024
 *
 */
public class SAXParserPractice {
	public static void main(String[] args) throws Exception {
		// 新建sax解析器的工厂
		SAXParserFactory saxFactory = SAXParserFactory.newInstance();
		// 不使用 DTD 验证
		saxFactory.setValidating(false);
		// 识别命名空间
		saxFactory.setNamespaceAware(true);
		// 使用工厂构建sax解析器
		SAXParser saxParser = saxFactory.newSAXParser();
		// 取得xmlReader
		XMLReader xmlReader = saxParser.getXMLReader();
		// 设置内容处理器
		StudentContentHandler contentHandler = new StudentContentHandler();
		xmlReader.setContentHandler(contentHandler);
		// 设置错误处理器
		StudentErrorHandler errorHandler = new StudentErrorHandler();
		xmlReader.setErrorHandler(errorHandler);
		// 解析，参数为systemId，可以是URI，还有一种叫publicId不怎么使用
		xmlReader.parse("student.xml");
	}

	// 内容处理器，除了setDocumentLocator之外的函数都是事件！遇到感兴趣的事件和内容时要保存相关内容，1p等10年！
	public static class StudentContentHandler implements ContentHandler {
		// 文档解析定位器
		Locator locator;

		// 打印位置函数，人工加入，便于显示
		public String getLocation() {
			return "(" + locator.getLineNumber() + "," + locator.getColumnNumber() + ")";
		}

		@Override
		// 获取内容处理器中的定位器，方便获取当前解析的位置
		public void setDocumentLocator(Locator locator) {
			this.locator = locator;
		}

		@Override
		// 检测到文档开始事件
		public void startDocument() throws SAXException {
			System.out.println("[startDocument" + getLocation() + "] Document Start");
		}

		@Override
		// 检测到文档结束事件
		public void endDocument() throws SAXException {
			System.out.println("[endDocument" + getLocation() + "] Document End");
		}

		@Override
		// 检测到开始前缀映射事件，一般发生在元素头
		public void startPrefixMapping(String prefix, String uri) throws SAXException {
			System.out.println("[startPrefixMapping" + getLocation() + "] prefix: ".concat(prefix));
			System.out.println("[startPrefixMapping" + getLocation() + "] uri: ".concat(uri));
		}

		@Override
		// 检测到结束前缀映射事件，一般发生在元素结尾
		public void endPrefixMapping(String prefix) throws SAXException {
			System.out.println("[endPrefixMapping" + getLocation() + "] prefix: ".concat(prefix));
		}

		@Override
		// 检测到元素开始事件，如果之前生成工厂时把识别ns设为false，则此处uri和localname为空串
		public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
			System.out.println("[startElement" + getLocation() + "] uri: ".concat(uri));
			System.out.println("[startElement" + getLocation() + "] localName: ".concat(localName));
			System.out.println("[startElement" + getLocation() + "] qName: ".concat(qName));
			for (int i = 0; i < atts.getLength(); i++) {
				System.out.println("[startElement" + getLocation() + "] Attr[".concat(atts.getQName(i)).concat("]: ").concat(atts.getValue(i)));
			}
		}

		@Override
		// 检测到元素结束事件
		public void endElement(String uri, String localName, String qName) throws SAXException {
			System.out.println("[endElement" + getLocation() + "] uri: ".concat(uri));
			System.out.println("[endElement" + getLocation() + "] localName: ".concat(localName));
			System.out.println("[endElement" + getLocation() + "] qName: ".concat(qName));
		}

		@Override
		// 检测到元素中的字符串事件，ch是附近（甚至整个）xml的内容，start是offset，length是字符串的长度，注意一个节点的收尾可能有空串节点
		public void characters(char[] ch, int start, int length) throws SAXException {
			String s = new String(ch, start, length);
			System.out.println("[characters" + getLocation() + "] string: ".concat(s));
		}

		@Override
		// 检测到元素中可忽略的字符串事件
		public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
			String s = new String(ch);
			System.out.println("[ignorableWhitespace" + getLocation() + "] string: ".concat(s));
		}

		@Override
		// 检测到处理指令的事件
		public void processingInstruction(String target, String data) throws SAXException {
			System.out.println("[processingInstruction" + getLocation() + "] target: ".concat(target));
			System.out.println("[processingInstruction" + getLocation() + "] data: ".concat(data));
		}

		@Override
		// 检测到可跳过实体的事件
		public void skippedEntity(String name) throws SAXException {
			System.out.println("[skippedEntity" + getLocation() + "] name: ".concat(name));
		}

	}

	public static class StudentErrorHandler implements ErrorHandler {

		@Override
		// 发现警告
		public void warning(SAXParseException exception) throws SAXException {
			System.err.println("[warning] ".concat(exception.getMessage()));
		}

		@Override
		// 发现可修复的错误，parser还能继续
		public void error(SAXParseException exception) throws SAXException {
			System.err.println("[error] ".concat(exception.getMessage()));
		}

		@Override
		// 发现不可修复的错误，parser不能继续
		public void fatalError(SAXParseException exception) throws SAXException {
			System.err.println("[fatalError] ".concat(exception.getMessage()));
		}

	}
}
