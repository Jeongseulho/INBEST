package com.jrjr.inbest.global.util;

import java.util.Optional;

import org.springframework.http.ResponseCookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {

	public static Optional<String> getCookieValue(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					return Optional.of(cookie.getValue());
				}
			}
		}
		return Optional.empty();
	}

	public static void createCookie(HttpServletResponse response, String name, String value) {
		ResponseCookie responseCookie = ResponseCookie.from(name, value)
			.path("/")
			.sameSite("None")
			.httpOnly(true)
			.build();
		response.setHeader("Set-Cookie", responseCookie.toString());
	}

	public static void deleteCookie(HttpServletResponse response, String name) {
		ResponseCookie responseCookie = ResponseCookie.from(name, "")
			.path("/")
			.sameSite("None")
			.maxAge(0)
			.build();
		response.setHeader("Set-Cookie", responseCookie.toString());
	}
}
