package com.gemserk.jnlpappletloader.util.jnlp.applet;

import java.util.HashMap;
import java.util.Map;

import com.gemserk.jnlpappletloader.util.jnlp.JnlpInfo;
import com.gemserk.jnlpappletloader.util.jnlp.JnlpResourceInfo;
import com.gemserk.jnlpappletloader.util.jnlp.JnlpResourceInfo.ResourceType;

/**
 * Helper class to build LWJGL AppletLoader needed parameters from a JNLPInfo.
 * 
 * @author acoppes
 * 
 */
public class AppletLoaderParametersBuilder {

	private final JnlpInfo jnlpInfo;

	private String appendedJarExtension;

	public AppletLoaderParametersBuilder(JnlpInfo jnlpInfo) {
		this.jnlpInfo = jnlpInfo;
		this.appendedJarExtension = "";
	}

	public Map<String, String> getParametersFromJnlpInfo() {
		Map<String, String> appletParameters = new HashMap<String, String>();

		if (jnlpInfo.jnlpAppletDescInfo != null) {
			appletParameters.putAll(jnlpInfo.jnlpAppletDescInfo.parameters);
			appletParameters.put("al_main", jnlpInfo.jnlpAppletDescInfo.mainClassName);
			appletParameters.put("al_title", jnlpInfo.jnlpAppletDescInfo.name);
		}

		String al_jars = getJarsForOsStartingWith("", ResourceType.Jar);
		System.out.println("jars: " + al_jars);
		appletParameters.put("al_jars", al_jars);

		addNativesFor(appletParameters, "Windows", "al_windows");
		addNativesFor(appletParameters, "Linux", "al_linux");
		addNativesFor(appletParameters, "Mac OS", "al_mac");
		addNativesFor(appletParameters, "Solaris", "al_solaris");
		addNativesFor(appletParameters, "FreeBSD", "al_freebsd");

		return appletParameters;
	}

	protected void addNativesFor(Map<String, String> appletParameters, String os, String appletParameter) {
		String parameter = getJarsForOsStartingWith(os, ResourceType.NativeLib);
		if ("".equals(parameter.trim())) {
			System.out.println(os + " has no natives");
			return;
		}
		System.out.println(os + " natives: " + parameter);
		appletParameters.put(appletParameter, parameter);
	}

	protected String getJarsForOsStartingWith(String os, ResourceType type) {

		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < jnlpInfo.resources.size(); i++) {
			JnlpResourceInfo jnlpResourceInfo = jnlpInfo.resources.get(i);

			if (jnlpResourceInfo.type != type)
				continue;

			if (!jnlpResourceInfo.os.toLowerCase().startsWith(os.toLowerCase()))
				continue;

			stringBuilder.append(jnlpResourceInfo.href);
			stringBuilder.append(this.appendedJarExtension);
			stringBuilder.append(", ");
		}

		if (stringBuilder.length() > 0)
			stringBuilder.replace(stringBuilder.length() - 2, stringBuilder.length(), "");

		return stringBuilder.toString();
	}

	public void setAppendedJarExtension(String appendedJarExtension) {
		this.appendedJarExtension = appendedJarExtension;
	}

}
