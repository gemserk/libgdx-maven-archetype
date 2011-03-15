package com.gemserk.jnlpappletloader.util.jnlp;

import java.util.HashMap;
import java.util.Map;

/**
 * Has the information of the <a href="http://download.oracle.com/javase/6/docs/technotes/guides/javaws/developersguide/syntax.html#applet_desc">applet-desc</a> of the JNLP based on the 
 * JNLP File Syntax page.
 */
public class JnlpAppletDescInfo {

	public String mainClassName;

	public String name;

	public Map<String, String> parameters = new HashMap<String, String>();

}