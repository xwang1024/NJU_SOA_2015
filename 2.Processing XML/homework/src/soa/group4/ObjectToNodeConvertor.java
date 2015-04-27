package soa.group4;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import soa.group4.annotation.MyList;
import soa.group4.annotation.MyNode;
import soa.group4.annotation.MyRootNode;
import soa.group4.exception.NotMyRootNodeException;

public class ObjectToNodeConvertor {
	/**
	 * 检查注解，判断是不是可以转换的对象
	 * @param object
	 * @return
	 */
	private static boolean checkAnnotation(Object object) {
		MyRootNode[] rootAnnos = object.getClass().getAnnotationsByType(MyRootNode.class);
		if (rootAnnos.length == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 将对象转换为Doc节点
	 * @param doc 对象基于的文档
	 * @param object 需要转换的对象
	 * @param name 自定义tag的名称，是可选参数，当出现多次时，只取index为0的字符串作为tag名
	 * @return
	 * @throws NotMyRootNodeException 没有实现相关的注解时会抛出
	 */
	public static Element convert(Document doc, final Object object, final String... name) throws NotMyRootNodeException {
		// 读取类的注解
		MyRootNode[] rootAnnos = object.getClass().getAnnotationsByType(MyRootNode.class);
		if (rootAnnos.length == 0) {
			throw new NotMyRootNodeException("Can't find MyRootNode annotation");
		}
		MyRootNode rootAnno = rootAnnos[0];

		// 读取这个类默认的Tag名称
		String rootName = rootAnno.name();
		if (name.length > 0) {
			rootName = name[0];
		}

		// 新建根元素
		Element element;
		if (rootAnno.NS().equals("")) {
			element = doc.createElement(rootName);
		} else {
			element = doc.createElementNS(rootAnno.NS(), rootName);
		}

		// 为了确定顺序，先把子元素放入map中
		HashMap<String, Element> map = new HashMap<String, Element>();

		// 对方法进行遍历
		Method[] methods = object.getClass().getMethods();
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getAnnotations().length > 0) {
				MyNode[] myElements = methods[i].getAnnotationsByType(MyNode.class);
				MyList[] myLists = methods[i].getAnnotationsByType(MyList.class);
				// 如果是使用了MyNode注解
				if (myElements.length > 0) {
					MyNode myElement = myElements[0];
					// 如果类型是Element
					if (myElement.type() == MyNode.Type.element) {
						Element node;
						Object returnObj;
						// 获取方法的返回值
						try {
							returnObj = methods[i].invoke(object);
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							e.printStackTrace();
							continue;
						}
						// 如果是复杂类型
						if (checkAnnotation(returnObj)) {
							node = convert(doc, returnObj, myElement.name());
						}
						// 如果是简单类型
						else {
							String namespace = rootAnno.NS();
							if (!myElement.NS().equals("")) {
								namespace = myElement.NS();
							}
							if (namespace.equals("")) {
								node = doc.createElement(myElement.name());
							} else {
								node = doc.createElementNS(namespace, myElement.name());
							}
							if (returnObj != null) {
								node.setTextContent(returnObj.toString());
							}
						}
						map.put(node.getTagName(), node);
					}
					// 如果类型是Attribute
					else {
						// TODO Attribute Element
					}
				}
				// 如果是使用了MyList注解
				else if (myLists.length > 0) {
					MyList myList = myLists[0];
					// 获得List的命名空间
					String namespace = rootAnno.NS();
					if (!myList.NS().equals("")) {
						namespace = myList.NS();
					}

					Element node;
					if (namespace.equals("")) {
						node = doc.createElement(myList.name());
					} else {
						node = doc.createElementNS(namespace, myList.name());
					}
					String itemNS = myList.itemNS();
					try {
						Object listObj = methods[i].invoke(object);
						if (listObj instanceof List<?>) {
							List<?> list = (List<?>) listObj;
							for (Object o : list) {
								Element childNode;
								if (checkAnnotation(o)) {
									childNode = convert(doc, o, myList.itemName());
								} else {
									if (itemNS.equals("")) {
										childNode = doc.createElement(myList.itemName());
									} else {
										childNode = doc.createElementNS(itemNS, myList.itemName());
									}
								}
								node.appendChild(childNode);
							}
						}
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
						continue;
					}

					map.put(node.getTagName(), node);
				}
			}
		}
		for (int i = 0; i < rootAnno.order().length; i++) {
			if (map.containsKey(rootAnno.order()[i])) {
				element.appendChild(map.get(rootAnno.order()[i]));
			} else {
				System.err.println("Can't find node: " + rootAnno.order()[i]);
			}
		}
		return element;
	}
}
