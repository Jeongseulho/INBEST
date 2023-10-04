package com.jrjr.inbest.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jrjr.inbest.user.dto.UserDto;
import com.jrjr.inbest.user.service.FriendService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "친구(팔로우/팔로워)", description = "친구 API")
public class FriendController {

	private final FriendService friendService;

	@Operation(summary = "팔로우 하기")
	@Parameters(value = {
		@Parameter(description = "로그인한 유저 pk(실제로는 자동으로 들어가므로 신경 X)", name = "loginSeq")
	})
	@PostMapping("/follow/{followingSeq}")
	public ResponseEntity<Map<String, Object>> follow(@PathVariable(name = "followingSeq") Long followingSeq,
		@RequestParam(name = "loginSeq") Long loginSeq) throws Exception {
		log.info("========== 팔로잉 시작 ==========");

		friendService.insertFriend(followingSeq, loginSeq);

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", true);

		log.info("========== 팔로잉 완료 ==========");
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@Operation(summary = "팔로우 취소하기")
	@Parameters(value = {
		@Parameter(description = "로그인한 유저 pk(실제로는 자동으로 들어가므로 신경 X)", name = "loginSeq")
	})
	@DeleteMapping("/follow/{followingSeq}")
	public ResponseEntity<Map<String, Object>> cancelFollow(
		@PathVariable(name = "followingSeq") Long followingSeq,
		@RequestParam(name = "loginSeq") Long loginSeq) throws Exception {
		log.info("========== 팔로우 취소 시작 ==========");

		friendService.deleteFriend(followingSeq, loginSeq);

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", true);

		log.info("========== 팔로우 취소 완료 ==========");
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@Operation(summary = "팔로잉 목록")
	@Parameters(value = {
		@Parameter(description = "로그인한 유저 pk(실제로는 자동으로 들어가므로 신경 X)", name = "loginSeq")
	})
	@GetMapping("/followings")
	ResponseEntity<Map<String, Object>> findAllFollowings(
		@RequestParam(name = "loginSeq") Long loginSeq) {
		log.info("========== 팔로잉 목록 시작 ==========");

		List<UserDto> followingList = friendService.findAllFollowings(loginSeq);

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", true);
		resultMap.put("followingList", followingList);

		log.info("========== 팔로잉 목록 완료 ==========");
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@Operation(summary = "팔로워 목록")
	@Parameters(value = {
		@Parameter(description = "로그인한 유저 pk(실제로는 자동으로 들어가므로 신경 X)", name = "loginSeq")
	})
	@GetMapping("/followers")
	ResponseEntity<Map<String, Object>> findAllFollowers(
		@RequestParam(name = "loginSeq") Long loginSeq) {
		log.info("========== 팔로워 목록 시작 ==========");

		List<UserDto> followerList = friendService.findAllFollowers(loginSeq);

		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("success", true);
		resultMap.put("followerList", followerList);

		log.info("========== 팔로워 목록 완료 ==========");
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
}
