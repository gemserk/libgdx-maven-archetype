package com.gemserk.jnlpappletloader.util.jnlp;

import static org.junit.Assert.assertThat;

import java.net.URL;

import org.hamcrest.core.IsNull;
import org.junit.Test;

public class ClassLoaderTest {

	@Test
	public void testClassLoaderGetResource() throws Exception {
		
		URL resource1 = Thread.currentThread().getContextClassLoader().getResource("/applet.jnlp");
		assertThat(resource1, IsNull.nullValue());

		URL resource2 = Thread.currentThread().getContextClassLoader().getResource("applet.jnlp");
		assertThat(resource2, IsNull.notNullValue());
		
		URL resource3 = ClassLoaderTest.class.getResource("/applet.jnlp");
		assertThat(resource3, IsNull.notNullValue());
		

	}

}
