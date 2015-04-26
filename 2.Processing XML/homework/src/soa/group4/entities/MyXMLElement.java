package soa.group4.entities;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class MyXMLElement {
	
	protected String getNSURL() {
		return "";
	}
	
	public Element toXMLElement(Document doc, String elementName) {
		Element element = doc.createElementNS(getNSURL(),elementName);
		Field[] fields = this.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			String varName = fields[i].getName();
			String tagName = varName.replace("_", "");
			Element child = doc.createElementNS(getNSURL(),tagName);
			
			if (MyXMLElement.class.isAssignableFrom(fields[i].getType())) {
				MyXMLElement xmlElement;
				try {
					xmlElement = (MyXMLElement) this.getClass().getMethod("get" + varName.toUpperCase().charAt(0) + varName.substring(1))
							.invoke(this);
					child = xmlElement.toXMLElement(doc, tagName);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
					continue;
				}
			} else if (List.class.isAssignableFrom(fields[i].getType())) {
				try {
					List<Object> list = (List<Object>) this.getClass().getMethod("get" + varName.toUpperCase().charAt(0) + varName.substring(1)).invoke(this);
					for(Object obj: list) {
						String listItemTag = obj.getClass().getSimpleName().toLowerCase().charAt(0) + obj.getClass().getSimpleName().substring(1);
						Element listChild = doc.createElementNS(getNSURL(),listItemTag);
						if (obj instanceof MyXMLElement) {
							listChild = ((MyXMLElement)obj).toXMLElement(doc, listItemTag);
						} else {
							listChild.setTextContent(obj+"");
							System.out.println(listChild.getNodeName().concat(" : ").concat(listChild.getTextContent()));
						}
						child.appendChild(listChild);
						System.out.println(child.getNamespaceURI() + ":" + child.getNodeName().concat(" : ").concat(child.getTextContent()));
					}
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
					continue;
				}

			} else if (Map.class.isAssignableFrom(fields[i].getType())) {
				System.out.println("TODO Map");
			} else {
				Object textContent;
				try {
					textContent = this.getClass().getMethod("get" + varName.toUpperCase().charAt(0) + varName.substring(1)).invoke(this);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
					continue;
				}
				child.setTextContent("" + textContent);
				System.out.println(child.getNamespaceURI() + ":" + child.getNodeName().concat(" : ").concat(child.getTextContent()));
			}
			element.appendChild(child);
		}
		return element;
	}
}
