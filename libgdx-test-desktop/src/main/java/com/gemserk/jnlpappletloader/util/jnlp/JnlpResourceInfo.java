package com.gemserk.jnlpappletloader.util.jnlp;


/**
 * Info of a single resource, could be a jar or a nativelib.
 */
public class JnlpResourceInfo {
	
	public static enum ResourceType { Jar, NativeLib, Extension }

	public String href;

	public String os;

	public JnlpResourceInfo.ResourceType type;

	public JnlpResourceInfo(String href, String os, JnlpResourceInfo.ResourceType type) {
		this.href = href;
		this.os = os;
		this.type = type;
	}

}