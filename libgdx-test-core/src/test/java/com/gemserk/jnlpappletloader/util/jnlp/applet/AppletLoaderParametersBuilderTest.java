package com.gemserk.jnlpappletloader.util.jnlp.applet;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.hamcrest.core.IsEqual;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gemserk.jnlpappletloader.util.jnlp.JnlpInfo;
import com.gemserk.jnlpappletloader.util.jnlp.JnlpResourceInfo;
import com.gemserk.jnlpappletloader.util.jnlp.JnlpResourceInfo.ResourceType;

@SuppressWarnings("serial")
@RunWith(JMock.class)
public class AppletLoaderParametersBuilderTest {

	Mockery mockery = new Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	@Test
	public void shouldGetOnlyNativesForOnePlatform() throws MalformedURLException {
		JnlpInfo jnlpInfo = new JnlpInfo();
		jnlpInfo.resources = new ArrayList<JnlpResourceInfo>() {
			{
				add(new JnlpResourceInfo("lwjgl.jar", "Windows", ResourceType.Jar));
				add(new JnlpResourceInfo("lwjgl-win.jar", "Windows", ResourceType.NativeLib));
				add(new JnlpResourceInfo("lwjgl-linux.jar", "Linux", ResourceType.NativeLib));
				add(new JnlpResourceInfo("lwjgl-mac.jar", "Mac OS", ResourceType.NativeLib));
			}
		};
		AppletLoaderParametersBuilder appletLoaderParametersBuilder = new AppletLoaderParametersBuilder(jnlpInfo);
		assertThat(appletLoaderParametersBuilder.getJarsForOsStartingWith("Windows", ResourceType.NativeLib), IsEqual.equalTo("lwjgl-win.jar"));
	}
	
	@Test
	public void shouldGetResourcesWithAdditionalExtension() throws MalformedURLException {
		JnlpInfo jnlpInfo = new JnlpInfo();
		jnlpInfo.resources = new ArrayList<JnlpResourceInfo>() {
			{
				add(new JnlpResourceInfo("lwjgl.jar", "", ResourceType.Jar));
				add(new JnlpResourceInfo("jinput.jar", "", ResourceType.Jar));
			}
		};
		
		AppletLoaderParametersBuilder appletLoaderParametersBuilder = new AppletLoaderParametersBuilder(jnlpInfo);
		appletLoaderParametersBuilder.setAppendedJarExtension(".pack.gz");
		assertThat(appletLoaderParametersBuilder.getJarsForOsStartingWith("", ResourceType.Jar), IsEqual.equalTo("lwjgl.jar.pack.gz, jinput.jar.pack.gz"));
	}

	@Test
	public void shouldGetOnlyAllNotNativeDependencies() throws MalformedURLException {
		JnlpInfo jnlpInfo = new JnlpInfo();
		jnlpInfo.resources = new ArrayList<JnlpResourceInfo>() {
			{
				add(new JnlpResourceInfo("lwjgl.jar", "", ResourceType.Jar));
				add(new JnlpResourceInfo("jinput.jar", "", ResourceType.Jar));
				add(new JnlpResourceInfo("lwjgl-win.jar", "Windows", ResourceType.NativeLib));
				add(new JnlpResourceInfo("lwjgl-linux.jar", "Linux", ResourceType.NativeLib));
				add(new JnlpResourceInfo("lwjgl-mac.jar", "Mac OS", ResourceType.NativeLib));
			}
		};
		AppletLoaderParametersBuilder appletLoaderParametersBuilder = new AppletLoaderParametersBuilder(jnlpInfo);
		assertThat(appletLoaderParametersBuilder.getJarsForOsStartingWith("", ResourceType.Jar), IsEqual.equalTo("lwjgl.jar, jinput.jar"));
	}

	@Test
	public void shouldGetAllResourcesForAGivenOS() throws MalformedURLException {
		JnlpInfo jnlpInfo = new JnlpInfo();
		jnlpInfo.resources = new ArrayList<JnlpResourceInfo>() {
			{
				add(new JnlpResourceInfo("lwjgl-win95.jar", "Windows 95", ResourceType.Jar));
				add(new JnlpResourceInfo("lwjgl-win98.jar", "Windows 98", ResourceType.Jar));
				add(new JnlpResourceInfo("lwjgl-win2000.jar", "Windows 2000", ResourceType.Jar));
			}
		};
		AppletLoaderParametersBuilder appletLoaderParametersBuilder = new AppletLoaderParametersBuilder(jnlpInfo);
		assertThat(appletLoaderParametersBuilder.getJarsForOsStartingWith("Windows", ResourceType.Jar), IsEqual.equalTo("lwjgl-win95.jar, lwjgl-win98.jar, lwjgl-win2000.jar"));
	}

	@Test
	public void shouldNotAddParameterIfNoNativesFound() {
		JnlpInfo jnlpInfo = new JnlpInfo();
		HashMap<String, String> appletParameters = new HashMap<String, String>();
		new AppletLoaderParametersBuilder(jnlpInfo).addNativesFor(appletParameters, "Windows", "al_windows");
		assertNull(appletParameters.get("al_windows"));
	}

	@Test
	public void shouldAddParameterIfNoNativesFound() {
		JnlpInfo jnlpInfo = new JnlpInfo();
		jnlpInfo.resources = new ArrayList<JnlpResourceInfo>() {
			{
				add(new JnlpResourceInfo("lwjgl.jar", "Windows", ResourceType.NativeLib));
			}
		};
		HashMap<String, String> appletParameters = new HashMap<String, String>();
		new AppletLoaderParametersBuilder(jnlpInfo).addNativesFor(appletParameters, "Windows", "al_windows");
		assertNotNull(appletParameters.get("al_windows"));
	}

}
