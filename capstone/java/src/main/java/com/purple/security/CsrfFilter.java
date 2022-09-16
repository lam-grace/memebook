package com.purple.security;

import java.io.IOException;
import java.security.SecureRandom;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bouncycastle.util.encoders.Base64;
import org.springframework.stereotype.Component;

@Component
public class CsrfFilter implements Filter {

	private static final String CSRF_TOKEN = "x-csrf-token";
	//to enforce CSRF, set to true
	private static final boolean enforceToken = false;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		if (httpRequest.getRequestURI().contains("csrf")) {
			//get new token
			createNewTokenAndCookie(httpResponse);
			httpResponse.setStatus(200);
			return;
		} else if (!isGET(httpRequest)) {
			//check token
			if (enforceToken) {
				String headerToken = httpRequest.getHeader(CSRF_TOKEN);
				String cookieToken = getCsrf(httpRequest);
				if (headerToken == null || cookieToken == null) {
					//no token
					httpResponse.sendError(400);
				} else if (!headerToken.equals(cookieToken)) {
					//bad token
					httpResponse.sendError(403);
				} else {
					httpResponse.setStatus(200);
				}
			} else {
				httpResponse.setStatus(200);
			}
		}
		if(!response.isCommitted()) {
			try {
				chain.doFilter(httpRequest, httpResponse);
			} catch (Exception e){
				httpResponse.sendError(403);
			}
		}
	}

	private void createNewTokenAndCookie(HttpServletResponse httpResponse) {
		String newToken = generateNewToken();
		httpResponse.setHeader(CSRF_TOKEN, newToken);
		//httpResponse.addCookie(new Cookie(CSRF_TOKEN, newToken));
		httpResponse.addCookie(new Cookie(CSRF_TOKEN,
				org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64.toBase64String(newToken.getBytes())));
	}

	private boolean isGET(HttpServletRequest httpRequest) {
		return httpRequest.getMethod().equalsIgnoreCase("GET");
	}

	private String generateNewToken() {
		SecureRandom random = new SecureRandom();
		return new String(Base64.encode(random.generateSeed(16)));
	}

	private String getCsrf(HttpServletRequest httpRequest) {
		Cookie[] cookies = httpRequest.getCookies();
		String cookieToken = null;
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(CSRF_TOKEN)) {
				//cookieToken = cookie.getValue();
				cookieToken = new String(org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64.decode(cookie.getValue()));
				break;
			}
		}
		return cookieToken;
	}

	@Override
	public void destroy() {
	}
}
