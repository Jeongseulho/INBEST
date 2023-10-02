package com.jrjr.inbest.global.filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DecodingFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest)request;
		Map<String, String[]> decodedParameters = new HashMap<>();

		for (Map.Entry<String, String[]> entry : httpRequest.getParameterMap().entrySet()) {
			String key = URLDecoder.decode(entry.getKey(), "UTF-8");
			String[] values = entry.getValue();
			for (int i = 0; i < values.length; i++) {
				values[i] = URLDecoder.decode(values[i], "UTF-8");
			}
			decodedParameters.put(key, values);
		}

		log.info("디코딩된 파라미터 ");

		for (String key : decodedParameters.keySet()) {
			log.info(key + " : " + Arrays.toString(decodedParameters.get(key)));
		}

		HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(httpRequest) {
			@Override
			public String getParameter(String name) {
				String[] values = decodedParameters.get(name);
				return values != null && values.length > 0 ? values[0] : null;
			}

			@Override
			public Map<String, String[]> getParameterMap() {
				return decodedParameters;
			}

			@Override
			public String[] getParameterValues(String name) {
				return decodedParameters.get(name);
			}
		};

		chain.doFilter(wrapper, response);
	}

	@Override
	public void init(FilterConfig filterConfig) {
		// Initialization code if necessary
	}
	// Initialization code if necessary

	@Override
	public void destroy() {
		// Cleanup code if necessary
	}
}