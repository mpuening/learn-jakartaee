package io.github.learnjakartaee.security.auth;

import java.io.Serializable;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

public class SavedRequest implements Serializable {

	private static final long serialVersionUID = 5755173745481968897L;

	private final String method;
	private final String url;
	private final String queryString;
	private final Map<String, List<String>> headers;
	private final Cookie[] cookies;

	public SavedRequest(String method, String url, String queryString,
			Map<String, List<String>> headers, Cookie[] cookies) {
		this.method = method;
		this.url = url;
		this.queryString = queryString;
		this.headers = headers;
		this.cookies = cookies;
	}

	public String getMethod() {
		return method;
	}

	public String getUrl() {
		return url;
	}

	public String getUrlWithQueryString() {
		return queryString == null ? url : url + "?" + queryString;
	}

	public String getQueryString() {
		return queryString;
	}

	public Map<String, List<String>> getHeaders() {
		return headers;
	}

	public Cookie[] getCookies() {
		return cookies;
	}

	public HttpServletRequest wrap(HttpServletRequest delegate) {
		return new HttpServletRequestWrapper(delegate) {
			@Override
			public String getMethod() {
				return method;
			}

			@Override
			public StringBuffer getRequestURL() {
				return new StringBuffer(url);
			}

			@Override
			public String getQueryString() {
				return queryString;
			}

			@Override
			public Enumeration<String> getHeaderNames() {
				return headers != null ? Collections.enumeration(headers.keySet()) : Collections.emptyEnumeration();
			}

			@Override
			public Enumeration<String> getHeaders(String name) {
				List<String> header = headers != null ? headers.get(name) : null;
				if (header == null) {
					header = Collections.emptyList();
				}
				return Collections.enumeration(header);
			}

			@Override
			public String getHeader(String name) {
				List<String> header = headers != null ? headers.get(name) : null;
				if (header == null || header.isEmpty()) {
					return null;
				}
				return header.get(0);
			}

			@Override
			public Cookie[] getCookies() {
				return cookies;
			}
		};
	}

	public static SavedRequest fromRequest(HttpServletRequest request) {
		String method = request.getMethod();
		String queryString = request.getQueryString();
		Map<String, List<String>> headers = new HashMap<>();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String name = headerNames.nextElement();
			headers.put(name, Collections.list(request.getHeaders(name)));
		}
		Cookie[] cookies = request.getCookies();

		SavedRequest savedRequest = new SavedRequest(
				method,
				request.getRequestURL().toString(),
				queryString,
				headers,
				cookies);
		return savedRequest;
	}
}