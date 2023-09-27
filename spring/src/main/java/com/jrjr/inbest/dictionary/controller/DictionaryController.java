package com.jrjr.inbest.dictionary.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jrjr.inbest.dictionary.dto.FinancialWordDTO;
import com.jrjr.inbest.dictionary.service.DictionaryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/financial-dictionary")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "금융 단어", description = "금융 단어 API")
public class DictionaryController {

	private final DictionaryService dictionaryService;

	@Operation(summary = "금융 단어 목록(검색어 포함)")
	@GetMapping("")
	@Parameters(value = {
		@Parameter(required = true, name = "pageNo", description = "페이지 번호"),
		@Parameter(required = true, name = "pageSize", description = "한번에 보여줄 글의 개수"),
	})
	public ResponseEntity<Map<String, Object>> findAllfiancialWords(@RequestParam(name = "pageNo") int page,
		@RequestParam(name = "pageSize") int size,
		@RequestParam(required = false) String keyword) throws Exception {
		log.info("========== 금융 단어 목록 검색 시작 ==========");
		log.info("page : " + page + " size : " + size+" keyword : "+keyword);


		List<FinancialWordDTO> financialWordDTOList = dictionaryService.findAllFinancialwords(page, size, keyword);
		log.info("검색 결과 : " + financialWordDTOList);

		//게시물 좋아요/덧글 수 통계 처리

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", true);
		resultMap.put("financial_dictionary", financialWordDTOList);

		log.info("========== 금융 단어 검색 종료 ==========");
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
}
