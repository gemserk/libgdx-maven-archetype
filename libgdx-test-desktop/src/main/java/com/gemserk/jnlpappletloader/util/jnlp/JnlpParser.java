package com.gemserk.jnlpappletloader.util.jnlp;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gemserk.jnlpappletloader.util.jnlp.JnlpResourceInfo.ResourceType;


/**
 * Parses a Jnlp document.
 * 
 * @author acoppes
 * 
 */
public class JnlpParser {

	public JnlpInfo parse(InputStream is) {
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

			Document document = documentBuilder.parse(is);

			return parse(document);
		} catch (Exception e) {
			throw new RuntimeException("Failed to parser jnlp from specified document", e);
		} finally {
			closeStream(is);
		}
	}

	private void closeStream(Closeable closeable) {
		try {
			closeable.close();
		} catch (IOException e) {
		}
	}

	/**
	 * Parses the Document and returns the JnlpInfo with all the JNLP information.
	 * 
	 * @return a JnlpInfo with the JNLP information.
	 */
	protected JnlpInfo parse(Document document) {
		JnlpInfo jnlpInfo = new JnlpInfo();

		NodeList jnlpElements = document.getElementsByTagName("jnlp");

		if (jnlpElements.getLength() == 0)
			throw new RuntimeException("Failed to parse document, jnlp file should have jnlp tag");

		Node jnlpElement = jnlpElements.item(0);

		jnlpInfo.codeBase = jnlpElement.getAttributes().getNamedItem("codebase").getNodeValue();

		NodeList childNodes = jnlpElement.getChildNodes();

		for (int i = 0; i < childNodes.getLength(); i++) {

			Node childNode = childNodes.item(i);

			if ("resources".equals(childNode.getNodeName()))
				getResourcesInfo(jnlpInfo, childNode);

		}

		NodeList appletDescElements = document.getElementsByTagName("applet-desc");

		if (appletDescElements.getLength() == 0)
			return jnlpInfo;

		jnlpInfo.jnlpAppletDescInfo = getAppletDescInfo(appletDescElements.item(0));

		return jnlpInfo;
	}

	private JnlpAppletDescInfo getAppletDescInfo(Node appletDescElement) {
		NamedNodeMap attributes = appletDescElement.getAttributes();

		String name = attributes.getNamedItem("name").getNodeValue();
		String mainClass = attributes.getNamedItem("main-class").getNodeValue();

		JnlpAppletDescInfo jnlpAppletDescInfo = new JnlpAppletDescInfo();
		jnlpAppletDescInfo.mainClassName = mainClass;
		jnlpAppletDescInfo.name = name;

		NodeList childNodes = appletDescElement.getChildNodes();

		for (int i = 0; i < childNodes.getLength(); i++) {

			Node childNode = childNodes.item(i);

			if ("param".equals(childNode.getNodeName()))
				getParamInfo(jnlpAppletDescInfo, childNode);

		}

		return jnlpAppletDescInfo;
	}

	private void getParamInfo(JnlpAppletDescInfo jnlpAppletDescInfo, Node childNode) {
		NamedNodeMap attributes = childNode.getAttributes();
		String nameAttribute = attributes.getNamedItem("name").getNodeValue();
		String valueAttribute = attributes.getNamedItem("value").getNodeValue();
		jnlpAppletDescInfo.parameters.put(nameAttribute, valueAttribute);
	}

	private void getResourcesInfo(JnlpInfo jnlpInfo, Node resourcesNode) {

		NamedNodeMap attributes = resourcesNode.getAttributes();

		String os = "";
		Node osAttribute = attributes.getNamedItem("os");

		if (osAttribute != null)
			os = osAttribute.getNodeValue();

		NodeList childNodes = resourcesNode.getChildNodes();

		for (int i = 0; i < childNodes.getLength(); i++) {

			Node childNode = childNodes.item(i);

			if ("jar".equals(childNode.getNodeName()))
				jnlpInfo.resources.add(getResourceInfo(childNode, os, ResourceType.Jar));

			if ("nativelib".equals(childNode.getNodeName()))
				jnlpInfo.resources.add(getResourceInfo(childNode, os, ResourceType.NativeLib));

			if ("extension".equals(childNode.getNodeName()))
				jnlpInfo.resources.add(getResourceInfo(childNode, os, ResourceType.Extension));

		}

	}

	private JnlpResourceInfo getResourceInfo(Node childNode, String os, ResourceType type) {
		NamedNodeMap attributes = childNode.getAttributes();
		Node hrefAttribute = attributes.getNamedItem("href");
		JnlpResourceInfo jnlpResourceInfo = new JnlpResourceInfo(hrefAttribute.getNodeValue(), os, type);
		return jnlpResourceInfo;
	}

}