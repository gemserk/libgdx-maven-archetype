package com.gemserk.jnlpappletloader.util.jnlp;

import java.net.URL;

import com.gemserk.jnlpappletloader.util.jnlp.JnlpResourceInfo.ResourceType;

public class JnlpMerger {

	private UrlBuilder urlBuilder;

	private JnlpParser jnlpParser;

	public void setJnlpParser(JnlpParser jnlpParser) {
		this.jnlpParser = jnlpParser;
	}

	public void setUrlBuilder(UrlBuilder urlBuilder) {
		this.urlBuilder = urlBuilder;
	}

	public void mergeWithExtensions(JnlpInfo jnlpInfo, URL codeBase) {

		while (jnlpInfo.hasExtensions()) {
			JnlpResourceInfo extensionResourceInfo = jnlpInfo.getFirstResource(ResourceType.Extension);
			jnlpInfo.removeResourceInfo(extensionResourceInfo);

			URL extensionUrl = urlBuilder.build(codeBase, extensionResourceInfo.href);
			JnlpInfo extensionJnlpInfo = jnlpParser.parse(urlBuilder.open(extensionUrl));
			mergeWithExtensions(extensionJnlpInfo, extensionUrl);

			URL extensionCodeBase = urlBuilder.build(extensionJnlpInfo.codeBase);
			for (int i = 0; i < extensionJnlpInfo.resources.size(); i++) {
				JnlpResourceInfo resourceInfo = extensionJnlpInfo.resources.get(i);
				URL resourceUrl = urlBuilder.build(extensionCodeBase, resourceInfo.href);
				jnlpInfo.resources.add(new JnlpResourceInfo(resourceUrl.toString(), resourceInfo.os, resourceInfo.type));
			}
		}

	}

}