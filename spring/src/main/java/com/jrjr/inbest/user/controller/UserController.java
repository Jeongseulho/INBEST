package com.jrjr.inbest.user.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jrjr.inbest.user.dto.JoinDto;
import com.jrjr.inbest.user.dto.SearchByNicknameDTO;
import com.jrjr.inbest.user.dto.UserDetailsDTO;
import com.jrjr.inbest.user.dto.UserDto;
import com.jrjr.inbest.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "회원 가입/마이페이지", description = "회원 API")
public class UserController {

	private final UserService userService;

	@Operation(summary = "회원 가입", description = "invest 회원 가입")
	@Parameters(value = {
		@Parameter(required = true, name = "email", description = "이메일(중복 불가능)"),
		@Parameter(required = true, name = "password", description = "비밀번호"),
		@Parameter(required = true, name = "name", description = "이름"),
		@Parameter(required = true, name = "nickname", description = "닉네임(중복 불가능)"),
		@Parameter(name = "birth", description = "생년월일(YYYY-MM-DD)"),
		@Parameter(name = "gender", description = "성별(1: 남자, 2: 여자, 0: 미제공)")
	})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200")
	})
	@PostMapping("")
	ResponseEntity<Map<String, Object>> join(@RequestBody JoinDto joinDto) {
		log.info("========== 회원 가입 시작 ==========");
		log.info("UserController - join 실행: {}", joinDto.getEmail());
		Map<String, Object> resultMap = new HashMap<>();

		userService.join(joinDto);

		log.info("========== 회원 가입 완료 ==========");
		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@Operation(summary = "이메일 존재 유무 확인")
	@Parameters(value = {
		@Parameter(required = true, name = "email", description = "이메일")
	})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "존재하는 이메일"),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 이메일")
	})
	@GetMapping("/inquiry-email")
	ResponseEntity<Map<String, Object>> checkEmailExists(@RequestParam String email) {
		log.info("UserController - checkEmailExists 실행: {}", email);
		Map<String, Object> resultMap = new HashMap<>();

		userService.checkEmailExists(email);

		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@Operation(summary = "닉네임 존재 유무 확인")
	@Parameters(value = {
		@Parameter(required = true, name = "nickname", description = "닉네임")
	})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "존재하는 닉네임"),
		@ApiResponse(responseCode = "404", description = "존재하지 않는 닉네임")
	})
	@GetMapping("/inquiry-nickname")
	ResponseEntity<Map<String, Object>> checkNicknameExists(@RequestParam String nickname) {
		log.info("UserController - checkNicknameExists 실행: {}", nickname);
		Map<String, Object> resultMap = new HashMap<>();

		userService.checkNicknameExists(nickname);

		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@Operation(summary = "비밀번호 변경", description = "필수 값: password")
	@Parameters(value = {
		@Parameter(required = true, name = "seq", description = "비밀번호를 변경 할 회원 pk값"),
		@Parameter(required = true, name = "password", description = "비밀번호")
	})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "401", description = "회원 정보 없음, 토큰의 이메일과 비밀번호를 변경하려는 계정의 이메일 불일치")
	})
	@PutMapping("/{seq}/password")
	ResponseEntity<Map<String, Object>> updatePassword(@PathVariable(value = "seq") Long seq,
		@RequestBody Map<String, String> passwordMap,
		@RequestParam Long loginSeq) {
		log.info("UserController - updatePassword 실행: {}", seq);
		Map<String, Object> resultMap = new HashMap<>();

		userService.updatePassword(seq, loginSeq, passwordMap.get("password"));

		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@Operation(summary = "회원 탈퇴")
	@Parameters(value = {
		@Parameter(required = true, name = "seq", description = "회원 탈퇴를 할 수정 할 회원 pk값")
	})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "401", description = "회원 정보 없음, 토큰의 이메일과 탈퇴하려는 계정의 이메일 불일치")
	})
	@DeleteMapping("/{seq}")
	ResponseEntity<Map<String, Object>> withdraw(@PathVariable(value = "seq") Long seq,
		@RequestParam Long loginSeq) {
		log.info("UserController - withdraw 실행: {}", seq);
		Map<String, Object> resultMap = new HashMap<>();

		userService.withdraw(seq, loginSeq);
		userService.deleteUserRankingInfo(seq);

		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@Operation(summary = "프로필 정보 조회 (seq, email, name, nickname, birth, gender, profileImgSearchName)")
	@Parameters(value = {
		@Parameter(required = true, name = "seq", description = "조회 할 회원 pk값")
	})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200",
			description = "반환: seq, email, name, nickname, birth, gender, profileImgSearchName"),
		@ApiResponse(responseCode = "404", description = "조회 회원 정보 없음")
	})
	@GetMapping("/{seq}")
	ResponseEntity<Map<String, Object>> getProfileInfo(@PathVariable(value = "seq") Long seq) {
		log.info("UserController - getProfile 실행: {}", seq);
		Map<String, Object> resultMap = new HashMap<>();

		UserDto userInfo = userService.getProfileInfo(seq);

		resultMap.put("UserInfo", userInfo);
		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@Operation(summary = "회원 정보 조회")
	@Parameters(value = {
		@Parameter(required = true, name = "seq", description = "조회 할 회원 pk값")
	})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "404", description = "조회 회원 정보 없음")
	})
	@GetMapping("/{seq}/details")
	ResponseEntity<Map<String, Object>> getUserDetailsInfo(@PathVariable(value = "seq") Long userSeq) {
		log.info("========== 회원({}) 정보 조회 시작 ==========", userSeq);
		Map<String, Object> resultMap = new HashMap<>();

		UserDetailsDTO userDetailsInfo = userService.getUserDetailsInfo(userSeq);

		log.info("========== 회원({}) 정보 조회 완료 ==========", userSeq);
		resultMap.put("UserDetailsInfo", userDetailsInfo);
		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@Operation(summary = "프로필 이미지: 기본 이미지로 변경")
	@Parameters(value = {
		@Parameter(required = true, name = "seq", description = "기본 이미지로 변경 할 회원 pk값")
	})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200"),
		@ApiResponse(responseCode = "401", description = "회원 정보 없음, 토큰의 이메일과 정보를 변경하려는 계정의 이메일 불일치")
	})
	@PutMapping("/{seq}/img")
	ResponseEntity<Map<String, Object>> updateProfileDefaultImg(@PathVariable(value = "seq") Long seq,
		@RequestParam(required = false) Long loginSeq) {
		log.info("UserController - updateProfileDefaultImg 실행: {}", seq);
		Map<String, Object> resultMap = new HashMap<>();

		userService.updateDefaultImg(seq, loginSeq);

		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@Operation(summary = "프로필 정보 업데이트",
		description = "필수 값: nickname 선택 값: birth, gender, MultipartFile")
	@Parameters(value = {
		@Parameter(name = "file", description = "프로필 사진"),
		@Parameter(name = "nickname", description = "닉네임"),
		@Parameter(name = "birth", description = "생년월일(YYYY-MM-DD)"),
		@Parameter(name = "gender", description = "성별(1: 남자, 2: 여자, 0: 미제공)"),
	})
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200",
			description = "반환: seq, email, name, nickname, birth, gender, profileImgSearchName"),
		@ApiResponse(responseCode = "401", description = "회원 정보 없음, 토큰의 이메일과 정보를 변경하려는 계정의 이메일 불일치")
	})
	@PutMapping("/{seq}")
	ResponseEntity<Map<String, Object>> updateProfile(@PathVariable(value = "seq") Long seq,
		@RequestParam(value = "file", required = false) MultipartFile file,
		@ModelAttribute UserDto userDto,
		@RequestParam Long loginSeq) throws IOException {
		log.info("========== 프로필 정보 업데이트 시작 ==========");
		log.info("회원 seq: {}", seq);
		Map<String, Object> resultMap = new HashMap<>();

		UserDto userInfo = userService.updateProfileInfo(seq, file, userDto, loginSeq);
		userService.updateUserRankingInfo(userInfo);

		log.info("========== 프로필 정보 업데이트 완료 ==========");
		resultMap.put("UserInfo", userInfo);
		resultMap.put("success", true);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@Operation(summary = "키워드가 포함된 닉네임 리스트 검색")
	@Parameters(value = {
		@Parameter(required = true, name = "keyword", description = "검색할 키워드")
	})
	@GetMapping("/nickname-list")
	ResponseEntity<Map<String, Object>> getUserSearchListByKeyword(@RequestParam String keyword) {
		log.info("========== '{}' 키워드가 포함된 닉네임 리스트 검색 시작 ==========", keyword);
		Map<String, Object> resultMap = new HashMap<>();

		List<SearchByNicknameDTO> searchList = userService.getUserSearchListByKeyword(keyword);

		log.info("========== '{}' 키워드가 포함된 닉네임 리스트 검색 완료 ==========", keyword);
		resultMap.put("success", true);
		resultMap.put("SearchList", searchList);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
}
