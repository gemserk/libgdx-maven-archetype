package com.gemserk.jnlpappletloader.util.jnlp;

import java.text.MessageFormat;


public class JnlpPrinter {
	
	public void printJnlpInfo(JnlpInfo jnlpInfo) {
		System.out.println("codeBase: " + jnlpInfo.codeBase);

		if (jnlpInfo.jnlpAppletDescInfo != null) {
			System.out.println("applet.mainClass: " + jnlpInfo.jnlpAppletDescInfo.mainClassName);
			System.out.println("applet.name: " + jnlpInfo.jnlpAppletDescInfo.name);
			System.out.println("applet.parameters: " + jnlpInfo.jnlpAppletDescInfo.parameters);
		}

		for (JnlpResourceInfo jnlpResourceInfo : jnlpInfo.resources)
			System.out.println(MessageFormat.format("resource: href={0}, os={1}, type={2}", jnlpResourceInfo.href, jnlpResourceInfo.os, jnlpResourceInfo.type));

	}
	
}