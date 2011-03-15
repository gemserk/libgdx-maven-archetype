package com.gemserk.jnlpappletloader.util.jnlp;

import static org.junit.Assert.assertThat;

import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.w3c.dom.Document;

import com.gemserk.jnlpappletloader.util.jnlp.JnlpResourceInfo.ResourceType;

@RunWith(JMock.class)
public class JnlpParserTest {

	Mockery mockery = new Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	@Test
	public void testParseJnlpFile() throws Exception {

		URL jnlpUrl = Thread.currentThread().getContextClassLoader().getResource("applet.jnlp");

		InputStream jnlpInputStream = jnlpUrl.openStream();

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

		Document document = documentBuilder.parse(jnlpInputStream);

		JnlpInfo jnlpInfo = new JnlpParser().parse(document);

		new JnlpPrinter().printJnlpInfo(jnlpInfo);

		jnlpInputStream.close();
	}
	
	@Test
	public void testParseWithExtension() throws Exception {
		JnlpParser jnlpParser = new JnlpParser();

		JnlpInfo jnlpInfo = jnlpParser.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("test-with-extensions.jnlp"));

		assertThat(jnlpInfo, IsNull.notNullValue());
		assertThat(jnlpInfo.hasExtensions(), IsEqual.equalTo(true));
		assertThat(jnlpInfo.resources.get(0).type, IsEqual.equalTo(ResourceType.Extension));
		assertThat(jnlpInfo.resources.get(1).type, IsEqual.equalTo(ResourceType.Extension));
		
		// assertThat(jnlpInfo.extensions.size(), IsEqual.equalTo(2));

		// JNLPInfo firstExtensionJnlpInfo = jnlpInfo.extensions.get(0);
		// assertThat(firstExtensionJnlpInfo.codeBase, IsEqual.equalTo("http://someplace.org/releases/"));
		//
		// JNLPInfo secondExtensionJnlpInfo = jnlpInfo.extensions.get(1);
		// assertThat(secondExtensionJnlpInfo.codeBase, IsEqual.equalTo("http://anotherplace.net/releases/"));

		new JnlpPrinter().printJnlpInfo(jnlpInfo);
	}

}
