package com.jrjr.inbest.dictionary.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jrjr.inbest.dictionary.dto.FinancialWordDTO;
import com.jrjr.inbest.dictionary.entity.FinancialWordEntity;
import com.jrjr.inbest.dictionary.repository.FinancialWordRepository;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DictionaryService {
	private final FinancialWordRepository financialWordRepository;

	public List<FinancialWordDTO> findAllFinancialwords(int page, int size, String keyword){
		Page<FinancialWordEntity> financialWordEntityList;

		if (keyword == null || keyword.equals("")) {
			financialWordEntityList = financialWordRepository.findAll(
				PageRequest.of(page - 1, size, Sort.Direction.DESC, "createdDate"));
		} else {
			financialWordEntityList = financialWordRepository.findAllByTitleRegexIgnoreCaseOrContentRegexIgnoreCase(keyword, keyword,
				PageRequest.of(page - 1, size, Sort.Direction.DESC, "createdDate"));
		}

		List<FinancialWordDTO> financialWordDTOList = new ArrayList<>();

		if(financialWordDTOList != null || financialWordDTOList.size() > 0){
			for(FinancialWordEntity financialWordEntity : financialWordEntityList){
				FinancialWordDTO financialWordDTO = financialWordEntity.toFinancialWordDTO();
				financialWordDTOList.add(financialWordDTO);
			}
		}
		return financialWordDTOList;
	}
}
