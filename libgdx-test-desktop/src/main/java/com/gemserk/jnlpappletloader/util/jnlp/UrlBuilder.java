package com.gemserk.jnlpappletloader.util.jnlp;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * URL Builder util class.
 */
public class UrlBuilder {

	private URL context;

	public UrlBuilder() {

	}

	public UrlBuilder(URL context) {
		this.context = context;
	}

	/**
	 * Returns an URL using the context set.
	 * 
	 * @param url
	 *            a String with the path of the URL to build, could be relative or absolute, if absolute then context is not used.
	 * @return an URL which could be relative to context or absolute.
	 */
	public URL build(String url) {
		return build(context, url);
	}

	/**
	 * Returns an URL using the context set.
	 * 
	 * @param context
	 *            url context to build.
	 * @param url
	 *            a String with the path of the URL to build, could be relative or absolute, if absolute then context is not used.
	 * @return an URL which could be relative to context or absolute.
	 */
	public URL build(URL context, String url) {
		try {
			return new URL(context, url);
		} catch (MalformedURLException e) {
			throw new RuntimeException("Failed to create url for " + url, e);
		}
	}

	public InputStream open(URL url) {
		try {
			return url.openStream();
		} catch (IOException e) {
			throw new RuntimeException("Failed to open input stream for url " + url, e);
		}
	}

}