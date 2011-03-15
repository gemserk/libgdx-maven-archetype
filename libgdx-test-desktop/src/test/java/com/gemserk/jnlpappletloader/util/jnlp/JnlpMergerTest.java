package com.gemserk.jnlpappletloader.util.jnlp;

import static org.junit.Assert.assertThat;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsNull;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gemserk.jnlpappletloader.util.jnlp.JnlpResourceInfo.ResourceType;

@RunWith(JMock.class)
public class JnlpMergerTest {

	Mockery mockery = new Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	@Test
	public void testMergeWithExtensions() throws MalformedURLException {

		final UrlBuilder urlBuilder = mockery.mock(UrlBuilder.class);
		final JnlpParser jnlpParser = mockery.mock(JnlpParser.class);
		final InputStream inputStream = mockery.mock(InputStream.class);

		JnlpMerger jnlpMerger = new JnlpMerger();
		jnlpMerger.setJnlpParser(jnlpParser);
		jnlpMerger.setUrlBuilder(urlBuilder);

		final URL jnlpUrl = new URL("file:somefile.jnlp");

		final JnlpInfo jnlpInfo = new JnlpInfo();
		jnlpInfo.codeBase = "jnlpcontext";
		jnlpInfo.resources.add(new JnlpResourceInfo("localjar.jar", "", ResourceType.Jar));
		jnlpInfo.resources.add(new JnlpResourceInfo("http://remote/remotejar.jar", "", ResourceType.Jar));
		jnlpInfo.resources.add(new JnlpResourceInfo("http://someplace.net/extension1.jnlp", "", ResourceType.Extension));

		final JnlpInfo extensionJnlpInfo = new JnlpInfo();
		extensionJnlpInfo.codeBase = "http://someplace.net/";
		extensionJnlpInfo.resources.add(new JnlpResourceInfo("lwjgl.jar", "", ResourceType.Jar));
		extensionJnlpInfo.resources.add(new JnlpResourceInfo("http://remote/remotejar2.jar", "", ResourceType.Jar));

		mockery.checking(new Expectations() {
			{
				oneOf(urlBuilder).build(jnlpUrl, "http://someplace.net/extension1.jnlp");
				will(returnValue(jnlpUrl));

				oneOf(urlBuilder).open(jnlpUrl);
				will(returnValue(inputStream));

				oneOf(jnlpParser).parse(inputStream);
				will(returnValue(extensionJnlpInfo));

				oneOf(urlBuilder).build("http://someplace.net/");
				will(returnValue(jnlpUrl));

				oneOf(urlBuilder).build(jnlpUrl, "lwjgl.jar");
				will(returnValue(new URL("http://someplace.net/lwjgl.jar")));

				oneOf(urlBuilder).build(jnlpUrl, "http://remote/remotejar2.jar");
				will(returnValue(new URL("http://remote/remotejar2.jar")));
			}
		});

		jnlpMerger.mergeWithExtensions(jnlpInfo, jnlpUrl);

		assertThat(jnlpInfo, IsNull.notNullValue());
		assertThat(jnlpInfo.hasExtensions(), IsEqual.equalTo(false));
		assertThat(jnlpInfo.resources.size(), IsEqual.equalTo(4));
		assertThat(jnlpInfo.resources.get(2).type, IsEqual.equalTo(ResourceType.Jar));
		assertThat(jnlpInfo.resources.get(2).href, IsEqual.equalTo("http://someplace.net/lwjgl.jar"));

		new JnlpPrinter().printJnlpInfo(jnlpInfo);
	}
	
}
