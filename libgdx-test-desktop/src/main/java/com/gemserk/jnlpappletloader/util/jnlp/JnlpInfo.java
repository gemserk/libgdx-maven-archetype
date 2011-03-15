package com.gemserk.jnlpappletloader.util.jnlp;

import java.util.ArrayList;
import java.util.List;

import com.gemserk.jnlpappletloader.util.jnlp.JnlpResourceInfo.ResourceType;



/**
 * Has the info of the JNLP file based on the <a href="http://download.oracle.com/javase/6/docs/technotes/guides/javaws/developersguide/syntax.html">JNLP file syntax</a>.
 */
public class JnlpInfo {
	
	// public String spec;

	public String codeBase;
	
	// public String href;
	
	// public String version;
	
	// public JnlpInformation information;

	public JnlpAppletDescInfo jnlpAppletDescInfo;

	public List<JnlpResourceInfo> resources = new ArrayList<JnlpResourceInfo>();
	
	public boolean hasExtensions() {
		for (int i = 0; i < resources.size(); i++) {
			if (resources.get(i).type == ResourceType.Extension)
				return true;
		}
		return false;
	}

	public JnlpResourceInfo getFirstResource(ResourceType type) {
		for (int i = 0; i < resources.size(); i++) {
			JnlpResourceInfo jnlpResourceInfo = resources.get(i);
			if (jnlpResourceInfo.type == type)
				return jnlpResourceInfo;
		}
		return null;
	}

	public void removeResourceInfo(JnlpResourceInfo jnlpResourceInfo) {
		resources.remove(jnlpResourceInfo);
	}

}