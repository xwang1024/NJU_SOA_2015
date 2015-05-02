package soa.group4.helper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class DocumentHelper {
	private Document doc;

	public DocumentHelper() {
	}

	public DocumentHelper(Document doc) {
		super();
		this.doc = doc;
	}

	public Document getDocument() {
		return doc;
	}

	public void initByCreate(boolean namespaceAware) throws Exception {
		doc = createDocument(namespaceAware);
	}

	private Document createDocument(boolean namespaceAware) throws Exception {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		docFactory.setNamespaceAware(namespaceAware);
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		return docBuilder.newDocument();
	}

	public void initByRead(String XMLPath, boolean namespaceAware) throws Exception {
		doc = readDocument(XMLPath, namespaceAware);
	}

	private Document readDocument(String XMLPath, boolean namespaceAware) throws Exception {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		docFactory.setNamespaceAware(namespaceAware);
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		return docBuilder.newDocument();
	}

	public Document transform(String XSLPath, boolean namespaceAware) throws Exception {
		StreamSource xslSource = new StreamSource(XSLPath);
		DOMSource domSource = new DOMSource(doc);
		Document output = createDocument(namespaceAware);
		DOMResult domResult = new DOMResult(output);
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(xslSource);
		transformer.transform(domSource, domResult);
		return output;
	}

	public boolean validate(String schemaPath) throws Exception {
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(new File(schemaPath));
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

	public File save(String path) throws Exception {
		TransformerFactory transFactory = TransformerFactory.newInstance();
		Transformer trans = transFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		File f = new File(path);
		Writer writer = new FileWriter(f);
		StreamResult xmlResult = new StreamResult(writer);
		trans.transform(source, xmlResult);
		return f;
	}
}
