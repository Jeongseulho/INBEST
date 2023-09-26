package com.jrjr.invest.simulation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jrjr.invest.simulation.dto.CreatedGroupDTO;
import com.jrjr.invest.simulation.dto.GroupDTO;
import com.jrjr.invest.simulation.dto.UserDTO;
import com.jrjr.invest.simulation.service.GroupService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/group")
@RestController
@RequiredArgsConstructor
@Slf4j
public class GroupController {

	private final GroupService groupService;

	// 그룹 생성
	@PostMapping("/")
	ResponseEntity<?> createGroup(@RequestBody CreatedGroupDTO groupDTO) throws Exception {
		groupService.createGroup(groupDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// 그룹 생성 시, 초대를 위한 유저 목록 검색
	@GetMapping("/")
	ResponseEntity<?> searchUsers(@RequestParam String keyword) {
		List<UserDTO> users = groupService.searchUsers(keyword);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	// 내 그룹 리스트
	@GetMapping("/my-list")
	ResponseEntity<?> listMyGroup(@RequestParam String userNickname) throws Exception {
		List<GroupDTO> groupDTOList = groupService.getMyGroupList(userNickname);
		return new ResponseEntity<>(groupDTOList, HttpStatus.OK);
	}

	// 참여 가능 그룹 리스트
	@GetMapping("/joinable-list")
	ResponseEntity<?> listJoinable(@RequestParam String userNickname) throws Exception {
		List<GroupDTO> groupDTOList = groupService.getJoinableList(userNickname);
		return new ResponseEntity<>(groupDTOList, HttpStatus.OK);
	}

	// 내 그룹 - 시작 전 상세
	// 내 그룹 - 진행 중 상세
	// 참여 가능 그룹 - 상세
	@GetMapping("/details")
	ResponseEntity<?> getDetails(@RequestParam Long simulationSeq, @RequestParam String progressState) {

		Object detailsDTO = groupService.getDetails(simulationSeq, progressState);
		// 내 그룹 - 시작 전 상세
		if (progressState == "waiting") {

		}
		// {seedMoney : long, period: int, averageTier : int, currentMemberImage : Array<string>, ownerSeq : long}
		// 내 그룹 - 진행 중 상세
		if (progressState == "inprogress") {

		}
		// {seedMoney : long, averageTier : int, rankInGroup : int, rankInGroupFluctuation : int, currentMemberImage : Array<string>, startDate : LocalDate, endDate : LocalDate}
		// 참여 가능 그룹 - 상세
		if (progressState == "waiting") {

		}
		// {simulationSeq : long, title : string, currentMemberNum : int, seedMoney : long, averageTier : int, period : int}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	// 그룹 참여하기
	// 그룹 나가기 | 방장 나가기
	// 모의 투자 시작
}
