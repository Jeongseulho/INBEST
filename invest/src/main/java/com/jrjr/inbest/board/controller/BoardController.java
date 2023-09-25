package com.jrjr.inbest.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jrjr.inbest.board.dto.BoardDTO;
import com.jrjr.inbest.board.dto.CommentDTO;
import com.jrjr.inbest.board.dto.UserDTO;
import com.jrjr.inbest.board.service.BoardService;
import com.jrjr.inbest.board.service.CommentService;
import com.jrjr.inbest.board.service.UserService;
import com.jrjr.inbest.global.jwt.JwtProvider;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/boards")
@Tag(name = "자유게시판(게시물, 댓글, 좋아요)", description = "자유게시판 API")
public class BoardController {
	private final BoardService boardService;
	private final CommentService commentService;
	private final JwtProvider jwtProvider;
	private final UserService userService;

	@Operation(summary = "게시판 등록")
	@Parameters(value = {
		@Parameter(required = true, name = "userSeq", description = "유저 pk"),
		@Parameter(required = true, name = "context", description = "글 내용"),
		@Parameter(required = true, name = "title", description = "글 제목"),
		@Parameter(required = false, name = "files", description = "글에 들어가는 파일 배열(type : File)"),
	})
	@PostMapping("")
	public ResponseEntity<Map<String, Object>> insertBoard(HttpServletRequest request,
		@RequestBody BoardDTO boardDTO) throws Exception {
		log.info("========== 게시판 등록 시작 ==========");
		log.info("입력 받은 데이터");
		log.info(boardDTO.toString());

		boardService.insertBoard(boardDTO);

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", true);

		log.info("========== 게시판 등록 종료 ==========");
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@PutMapping("/{boardId}")
	public ResponseEntity<Map<String, Object>> updateBoard(
		@PathVariable String boardId,
		@RequestBody BoardDTO boardDTO,
		HttpServletRequest request) throws Exception {
		log.info("========== 게시판 수정 시작 ==========");
		log.info("입력 받은 데이터");
		log.info(boardDTO.toString());

		//토큰으로 유저 이메일 얻기
		String loginEmail = jwtProvider.getEmail(request);
		log.info("로그인 유저 이메일 : " + loginEmail);

		BoardDTO originalBoard = boardService.findBySeq(boardId);

		if (!loginEmail.equals(originalBoard.getWriter().getEmail())) {
			throw new Exception("로그인한 유저와 게시물의 작성자와 다릅니다.");
		}

		boardService.updateBoard(boardDTO);

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", true);

		log.info("========== 게시판 수정 종료 ==========");
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@DeleteMapping("/{boardId}")
	public ResponseEntity<Map<String, Object>> deleteBoard(
		@PathVariable String boardId, HttpServletRequest request) throws Exception {
		log.info("========== 게시판 삭제 시작 ==========");
		log.info("입력 받은 데이터");
		log.info(boardId);

		//토큰으로 유저 이메일 얻기
		String loginEmail = jwtProvider.getEmail(request);
		log.info("로그인 유저 이메일 : " + loginEmail);

		BoardDTO originalBoard = boardService.findBySeq(boardId);

		if (!loginEmail.equals(originalBoard.getWriter().getEmail())) {
			throw new Exception("로그인한 유저와 게시물의 작성자와 다릅니다.");
		}

		boardService.deleteBoard(boardId);

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", true);

		log.info("========== 게시판 삭제 종료 ==========");
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@Operation(summary = "게시판 목록 출력", description = "기간(period)와 한번에 출력하는 양(size)를 이용해 지정된 범위 내의 게시물을 가장 최신 순서로 찾는 기능")
	@Parameters(value = {
		@Parameter(required = true, name = "pageNo", description = "페이지 번호"),
		@Parameter(required = true, name = "pageSize", description = "한번에 보여줄 글의 개수"),
	})
	@GetMapping("")
	public ResponseEntity<Map<String, Object>> findAllBoards(@RequestParam(name = "pageNo") int page,
		@RequestParam(name = "pageSize") int size,
		HttpServletRequest request) throws Exception {
		log.info("========== 게시판 목록 검색 시작 ==========");
		log.info("page : " + page + " size : " + size);

		List<BoardDTO> boardDTOList = boardService.findAllBoards(page, size);

		log.info("검색 결과 : " + boardDTOList);

		//토큰으로 유저 이메일 얻기
		String loginEmail = "";

		try {
			loginEmail = jwtProvider.getEmail(request);
			log.info("로그인 유저 이메일 : " + loginEmail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		loginEmail = jwtProvider.getEmail(request);

		//로그인한 유저가 좋아요를 누른 경우 처리
		for (int i = 0; i < boardDTOList.size(); i++) {
			List<UserDTO> likeUserList = boardDTOList.get(i).getLikesUserList();
			for (int j = 0; j < likeUserList.size(); j++) {
				if (likeUserList.get(j).getEmail().equals(loginEmail)) {
					boardDTOList.get(i).setLoginLike(true);
				}
			}
		}

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", true);
		resultMap.put("board", boardDTOList);
		resultMap.put("total", boardDTOList.size());
		log.info("========== 게시판 검색 종료 ==========");
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@Operation(summary = "좋아요가 많은 게시판 목록", description = "기간(period)와 한번에 출력하는 양(pageSize)를 이용해 지정된 범위 내의 게시물을 가장 최신 순서로 찾는 기능")
	@Parameters(value = {
		@Parameter(required = true, name = "pageSize", description = "한번에 보여줄 글의 개수"),
		@Parameter(required = true, name = "period", description = "탐색 기간 ex) 3 : 3일전 ~ 현재까지 "),
	})
	@GetMapping("/most-likes")
	public ResponseEntity<Map<String, Object>> findAllLikesBoards(
		@RequestParam(name = "pageSize") int pageSize,
		@RequestParam(name = "period") int period,
		HttpServletRequest request) {
		log.info("========== 좋아요 많은 게시판 목록 검색 시작 ==========");
		log.info("페이지 크기 : " + pageSize + " 기간 : " + period);

		List<BoardDTO> boardDTOList = boardService.findMostLikesPosts(pageSize, period);
		//토큰으로 유저 이메일 얻기
		String loginEmail = "";

		try {
			loginEmail = jwtProvider.getEmail(request);
			log.info("로그인 유저 이메일 : " + loginEmail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		loginEmail = jwtProvider.getEmail(request);

		//로그인한 유저가 좋아요를 누른 경우 처리
		for (int i = 0; i < boardDTOList.size(); i++) {
			List<UserDTO> likeUserList = boardDTOList.get(i).getLikesUserList();
			for (int j = 0; j < likeUserList.size(); j++) {
				if (likeUserList.get(j).getEmail().equals(loginEmail)) {
					boardDTOList.get(i).setLoginLike(true);
				}
			}
		}
		log.info("검색 결과 : " + boardDTOList);

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", true);
		resultMap.put("board", boardDTOList);
		resultMap.put("total", boardDTOList.size());

		log.info("========== 좋아요 많은 게시판 목록 검색 종료 ==========");
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@GetMapping("/most-views")
	public ResponseEntity<Map<String, Object>> findAllViewBoards(
		@RequestParam(name = "pageSize") int pageSize,
		@RequestParam(name = "period") int period,
		HttpServletRequest request) {
		log.info("========== 조회수 많은 게시판 목록 검색 시작 ==========");
		log.info("페이지 크기 : " + pageSize + " 기간 : " + period);

		List<BoardDTO> boardDTOList = boardService.findMostLikesPosts(pageSize, period);

		log.info("검색 결과 : " + boardDTOList);
		//토큰으로 유저 이메일 얻기
		String loginEmail = "";

		try {
			loginEmail = jwtProvider.getEmail(request);
			log.info("로그인 유저 이메일 : " + loginEmail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		loginEmail = jwtProvider.getEmail(request);

		//로그인한 유저가 좋아요를 누른 경우 처리
		for (int i = 0; i < boardDTOList.size(); i++) {
			List<UserDTO> likeUserList = boardDTOList.get(i).getLikesUserList();
			for (int j = 0; j < likeUserList.size(); j++) {
				if (likeUserList.get(j).getEmail().equals(loginEmail)) {
					boardDTOList.get(i).setLoginLike(true);
				}
			}
		}
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", true);
		resultMap.put("board", boardDTOList);
		resultMap.put("total", boardDTOList.size());

		log.info("========== 조회수 많은 게시판 목록 검색 종료 ==========");
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@Operation(summary = "게시판 상세정보")
	@GetMapping("/{seq}")
	public ResponseEntity<Map<String, Object>> findBoardBySeq(@PathVariable(value = "seq") String seq,
		HttpServletRequest request) throws Exception {
		log.info("========== 게시판 상세 정보 시작 ==========");
		log.info("seq : " + seq);

		BoardDTO boardDTO = boardService.findBySeq(seq);
		String loginEmail = "";
		log.info("검색 결과 : " + boardDTO);
		//토큰으로 유저 이메일 얻기
		try {
			loginEmail = jwtProvider.getEmail(request);
			log.info("로그인 유저 이메일 : " + loginEmail);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> resultMap = new HashMap<>();
		if (boardDTO.getSeq() == null || boardDTO.getSeq().isEmpty()) {
			resultMap.put("success", false);
		} else {
			resultMap.put("success", true);
			boolean like = false;

			for (UserDTO likeUserDTO : boardDTO.getLikesUserList()) {
				if (likeUserDTO.getEmail().equals(loginEmail)) {
					like = true;
				}
			}
			resultMap.put("loginUserLike", like);
		}
		resultMap.put("board", boardDTO);

		log.info("========== 게시판 상세 정보 종료 ==========");
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@Operation(summary = "게시물 좋아요")
	@PutMapping("/{boardSeq}/likes")
	public ResponseEntity<Map<String, Object>> updateBoardLikes(
		@PathVariable(value = "boardSeq") String boardId,
		HttpServletRequest request) throws
		Exception {
		log.info("========== 게시판 좋아요 시작 ==========");
		//토큰으로 유저 이메일 얻기
		String loginEmail = jwtProvider.getEmail(request);
		if (loginEmail.equals("")) {
			throw new Exception("로그인 이메일 정보가 없습니다.");
		}

		log.info("로그인 유저 이메일 : " + loginEmail);
		UserDTO userDTO = userService.findByEmail(loginEmail);
		if (loginEmail.equals("")) {
			throw new Exception("해당 이메일의 유저 정보가 없습니다.");
		}

		log.info("게시판 seq " + boardId);
		BoardDTO boardDTO = boardService.updateLikes(userDTO.getSeq(), boardId);

		log.info("좋아요 결과 : " + boardDTO);
		Map<String, Object> resultMap = new HashMap<>();

		if (boardDTO.getSeq() == null || boardDTO.getSeq().isEmpty()) {
			resultMap.put("success", false);
		} else {
			resultMap.put("success", true);
		}

		resultMap.put("board", boardDTO);

		log.info("========== 게시판 좋아요 종료 ==========");
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@Operation(summary = "댓글 등록")
	@Parameters(value = {
		@Parameter(required = true, name = "userSeq", description = "댓글 작성자 pk"),
		@Parameter(required = true, name = "context", description = "댓글 내용"),
	})
	@PostMapping("/{boardSeq}/comments")
	public ResponseEntity<Map<String, Object>> insertComment(
		@RequestBody CommentDTO commentDTO, @PathVariable(name = "boardSeq") String boardSeq) throws
		Exception {
		log.info("========== 덧글 등록 시작 ==========");
		log.info("덧글 : " + commentDTO);

		CommentDTO resultDto = commentService.insertComment(commentDTO, boardSeq);

		log.info("덧글 결과 : " + resultDto);

		Map<String, Object> resultMap = new HashMap<>();

		if (resultDto.getSeq() == null || resultDto.getSeq().isEmpty()) {
			resultMap.put("success", false);
		} else {
			resultMap.put("success", true);
		}

		resultMap.put("comment", resultDto);

		log.info("========== 덧글 등록 종료 ==========");
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@PutMapping("/{boardSeq}/comments/{commentSeq}")
	public ResponseEntity<Map<String, Object>> updateComment(
		@RequestBody CommentDTO commentDTO, @PathVariable(name = "boardSeq") String boardSeq,
		@PathVariable(name = "commentSeq") String commentSeq,
		HttpServletRequest request) throws
		Exception {
		log.info("========== 덧글 수정 시작 ==========");
		commentDTO.setSeq(commentSeq);
		log.info("덧글 : " + commentDTO);
		log.info(commentDTO.toString());

		//토큰으로 유저 이메일 얻기
		String loginEmail = jwtProvider.getEmail(request);
		log.info("로그인 유저 이메일 : " + loginEmail);

		CommentDTO originalComment = commentService.findCommentBySeq(commentSeq);
		UserDTO writer = userService.findBySeq(originalComment.getUserSeq());

		log.info("원본 댓글 : " + originalComment);

		if (!loginEmail.equals(writer.getEmail())) {
			throw new Exception("로그인한 유저와 게시물의 작성자와 다릅니다.");
		}

		commentDTO = commentService.updateComment(commentDTO);
		log.info("덧글 수정 결과 : " + commentDTO);

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", true);
		resultMap.put("comment", commentDTO);

		log.info("========== 덧글 수정 종료 ==========");
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@DeleteMapping("/{boardSeq}/comments/{commentSeq}")
	public ResponseEntity<Map<String, Object>> deleteComment(
		@PathVariable(name = "boardSeq") String boardSeq,
		@PathVariable(name = "commentSeq") String commentSeq,
		HttpServletRequest request) throws
		Exception {
		log.info("========== 덧글 삭제 시작 ==========");
		log.info("덧글 id : " + commentSeq);

		//토큰으로 유저 이메일 얻기
		String loginEmail = jwtProvider.getEmail(request);
		log.info("로그인 유저 이메일 : " + loginEmail);

		CommentDTO originalComment = commentService.findCommentBySeq(commentSeq);
		log.info("원본 댓글 : " + originalComment);
		UserDTO writer = userService.findBySeq(originalComment.getUserSeq());

		if (!loginEmail.equals(writer.getEmail())) {
			throw new Exception("로그인한 유저와 게시물의 작성자와 다릅니다.");
		}

		commentService.deleteComment(commentSeq);

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", true);

		log.info("========== 덧글 삭제 종료 ==========");
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@Operation(summary = "대댓글 등록")
	@Parameters(value = {
		@Parameter(required = true, name = "userSeq", description = "댓글 작성자 pk"),
		@Parameter(required = true, name = "context", description = "댓글 내용"),
	})
	@PostMapping("/{boardSeq}/comments/{commentSeq}/cocomments")
	public ResponseEntity<Map<String, Object>> insertCoComment(
		@RequestBody CommentDTO commentDTO, @PathVariable(name = "boardSeq") String boardSeq
		, @PathVariable(name = "commentSeq") String commentSeq) throws
		Exception {
		log.info("========== 대댓글 등록 시작 ==========");
		log.info("대댓글 : " + commentDTO);

		CommentDTO resultDto = commentService.insertCocomment(commentDTO, boardSeq, commentSeq);

		log.info("덧글 결과 : " + resultDto);

		Map<String, Object> resultMap = new HashMap<>();

		if (resultDto.getSeq() == null || resultDto.getSeq().isEmpty()) {
			resultMap.put("success", false);
		} else {
			resultMap.put("success", true);
		}

		resultMap.put("comment", resultDto);

		log.info("========== 대댓글 등록 종료 ==========");
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@PutMapping("/{boardSeq}/comments/{commentSeq}/cocomments/{cocommentSeq}")
	public ResponseEntity<Map<String, Object>> updateComment(
		@RequestBody CommentDTO cocommentDTO, @PathVariable(name = "boardSeq") String boardSeq,
		@PathVariable(name = "commentSeq") String commentSeq,
		@PathVariable(name = "cocommentSeq") String cocommentSeq,
		HttpServletRequest request) throws
		Exception {
		log.info("========== 대댓글 수정 시작 ==========");
		cocommentDTO.setSeq(cocommentSeq);
		log.info("대댓글 : " + cocommentDTO);
		log.info(cocommentDTO.toString());

		//토큰으로 유저 이메일 얻기
		String loginEmail = jwtProvider.getEmail(request);
		log.info("로그인 유저 이메일 : " + loginEmail);

		CommentDTO originalCocomment = commentService.findCocommentBySeq(cocommentSeq);
		UserDTO writer = userService.findBySeq(originalCocomment.getUserSeq());

		log.info("원본 대댓글 : " + originalCocomment);

		if (!loginEmail.equals(writer.getEmail())) {
			throw new Exception("로그인한 유저와 게시물의 작성자와 다릅니다.");
		}

		cocommentDTO = commentService.updateCocomment(cocommentDTO);
		log.info("대댓글 수정 결과 : " + cocommentDTO);

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", true);
		resultMap.put("comment", cocommentDTO);

		log.info("========== 대댓글 수정 종료 ==========");
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@DeleteMapping("/{boardSeq}/comments/{commentSeq}/cocomments/{cocommentSeq}")
	public ResponseEntity<Map<String, Object>> deleteCocomment(
		@PathVariable(name = "boardSeq") String boardSeq,
		@PathVariable(name = "commentSeq") String commentSeq,
		@PathVariable(name = "cocommentSeq") String cocommentSeq,
		HttpServletRequest request) throws
		Exception {
		log.info("========== 대댓글 삭제 시작 ==========");
		log.info("덧글 id : " + cocommentSeq);

		//토큰으로 유저 이메일 얻기
		String loginEmail = jwtProvider.getEmail(request);
		log.info("로그인 유저 이메일 : " + loginEmail);

		CommentDTO originalComment = commentService.findCocommentBySeq(cocommentSeq);
		log.info("원본 대댓글 : " + originalComment);
		UserDTO writer = userService.findBySeq(originalComment.getUserSeq());

		if (!loginEmail.equals(writer.getEmail())) {
			throw new Exception("로그인한 유저와 게시물의 작성자와 다릅니다.");
		}

		commentService.deleteCocomment(cocommentSeq);

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", true);

		log.info("========== 대댓글 삭제 종료 ==========");
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
}
