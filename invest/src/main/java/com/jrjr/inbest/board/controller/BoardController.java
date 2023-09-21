package com.jrjr.inbest.board.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jrjr.inbest.board.dto.BoardDTO;
import com.jrjr.inbest.board.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/boards")
public class BoardController {
	private final BoardService boardService;

	@PostMapping("")
	public ResponseEntity<Map<String, Object>> insertBoard(@ModelAttribute BoardDTO boardDTO) throws Exception {
		log.info("========== 게시판 등록 시작 ==========");

		log.info("입력 받은 데이터");
		log.info(boardDTO.toString());

		boardService.insertBoard(boardDTO);

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success",true);

		log.info("========== 게시판 등록 종료 ==========");
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
	@GetMapping("")
	public ResponseEntity<Map<String, Object>> findAllBoards(@RequestParam(name = "page") int page,@RequestParam(name = "size") int size){
		log.info("========== 게시판 목록 검색 시작 ==========");
		log.info("page : "+page+" size : "+size);

		List<BoardDTO> boardDTOList= boardService.findAllBoards(page,size);

		log.info("검색 결과 : "+boardDTOList);

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success",true);
		resultMap.put("board",boardDTOList);

		log.info("========== 게시판 등록 종료 ==========");
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
	@GetMapping("/best-likes")
	public ResponseEntity<Map<String, Object>> findAllLikesBoards(
		@RequestParam(name = "pageSize") int pageSize,
		@RequestParam(name = "period") int period){
		log.info("========== 좋아요 많은 게시판 목록 검색 시작 ==========");
		log.info("페이지 크기 : "+pageSize+" 기간 : "+period);

		List<BoardDTO> boardDTOList= boardService.findPopularPosts(pageSize,period);

		log.info("검색 결과 : "+boardDTOList);

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success",true);
		resultMap.put("board",boardDTOList);

		log.info("========== 좋아요 많은 게시판 목록 검색 종료 ==========");
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@GetMapping("/{seq}")
	public ResponseEntity<Map<String, Object>> findBoardBySeq(@PathVariable (value = "seq") String seq){
		log.info("========== 게시판 상세 정보 시작 ==========");
		log.info("seq : "+seq);

		BoardDTO boardDTO= boardService.findBySeq(seq);

		log.info("검색 결과 : "+boardDTO);

		Map<String, Object> resultMap = new HashMap<>();
		if(boardDTO.getSeq() == null || boardDTO.getSeq().isEmpty()){
			resultMap.put("success",false);
		}else{
			resultMap.put("success",true);
		}
		resultMap.put("board",boardDTO);

		log.info("========== 게시판 상세 정보 종료 ==========");
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@PutMapping("/{boardSeq}/likes/{userSeq}")
	public ResponseEntity<Map<String, Object>> updateBoardLikes(
		@PathVariable (value = "boardSeq") String boardId,@PathVariable (value = "userSeq") Long userSeq) throws
		Exception {
		log.info("========== 게시판 좋아요 시작 ==========");
		log.info("유저 seq : "+userSeq+" 게시판 seq "+boardId);

		BoardDTO boardDTO= boardService.updateLikes(userSeq,boardId);

		log.info("좋아요 결과 : "+boardDTO);

		Map<String, Object> resultMap = new HashMap<>();

		if(boardDTO.getSeq() == null || boardDTO.getSeq().isEmpty()){
			resultMap.put("success",false);
		}else{
			resultMap.put("success",true);
		}

		resultMap.put("board",boardDTO);

		log.info("========== 게시판 좋아요 종료 ==========");
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

}
