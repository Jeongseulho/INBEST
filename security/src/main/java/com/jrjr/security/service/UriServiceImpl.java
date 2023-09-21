package com.jrjr.security.service;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.jrjr.security.entity.Uri;
import com.jrjr.security.repository.UriRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UriServiceImpl implements UriService {

	private final UriRepository uriRepository;

	@Override
	public Boolean matchUri(String inputUri) {
		List<Uri> uris = uriRepository.findAll();
		for (Uri uri : uris) {
			Pattern pattern = Pattern.compile(uri.getUri());
			if (pattern.matcher(inputUri).find()) {
				return true;
			}
		}
		return false;
	}
}
