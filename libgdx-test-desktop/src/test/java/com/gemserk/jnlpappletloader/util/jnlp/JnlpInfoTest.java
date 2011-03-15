package com.gemserk.jnlpappletloader.util.jnlp;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.hamcrest.core.IsSame;
import org.junit.Test;

import com.gemserk.jnlpappletloader.util.jnlp.JnlpResourceInfo.ResourceType;

public class JnlpInfoTest {

	@Test
	public void testHasExtensions() {
		JnlpInfo jnlpInfo = new JnlpInfo();
		assertThat(jnlpInfo.hasExtensions(), IsEqual.equalTo(false));
		jnlpInfo.resources.add(new JnlpResourceInfo("http://someplace.net", "", ResourceType.Extension));
		assertThat(jnlpInfo.hasExtensions(), IsEqual.equalTo(true));
	}

	@Test
	public void testGetFirstResourceMatching() {
		JnlpInfo jnlpInfo = new JnlpInfo();

		JnlpResourceInfo first = new JnlpResourceInfo("http://someplace.net/1", "", ResourceType.Extension);
		JnlpResourceInfo second = new JnlpResourceInfo("http://someplace.net/2", "", ResourceType.Extension);

		jnlpInfo.resources.add(first);
		jnlpInfo.resources.add(second);

		assertThat(jnlpInfo.getFirstResource(ResourceType.Extension), IsSame.sameInstance(first));
	}

	@Test
	public void testRemoveResourceInfo() {
		JnlpInfo jnlpInfo = new JnlpInfo();

		JnlpResourceInfo first = new JnlpResourceInfo("http://someplace.net/1", "", ResourceType.Extension);
		JnlpResourceInfo second = new JnlpResourceInfo("http://someplace.net/2", "", ResourceType.Extension);

		jnlpInfo.resources.add(first);
		jnlpInfo.resources.add(second);

		jnlpInfo.removeResourceInfo(first);
		assertThat(jnlpInfo.getFirstResource(ResourceType.Extension), IsSame.sameInstance(second));
	}

}
