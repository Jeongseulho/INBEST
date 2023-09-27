package com.jrjr.invest.simulation.controller;

import java.io.UnsupportedEncodingException;
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
import com.jrjr.invest.simulation.dto.GroupUserDTO;
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
	@PostMapping()
	ResponseEntity<?> createGroup(@RequestBody CreatedGroupDTO groupDTO) throws Exception {
		log.info("[그룹 생성 시작]");
		log.info("[입력 파라미터 : " + groupDTO + " ]");

		groupService.createGroup(groupDTO);

		log.info("[그룹 생성 끝]");
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// 그룹 생성 시, 초대를 위한 유저 목록 검색
	@GetMapping()
	ResponseEntity<?> searchUsers(@RequestParam String keyword) throws UnsupportedEncodingException {
		log.info("===== 유저 목록 검색 시작 =====");
		log.info("입력받은 검색어 : "+keyword);
		
		List<UserDTO> users = groupService.searchUsers(keyword);

		log.info("검색한 유저 정보");
		log.info(users.toString());

		log.info("===== 유저 목록 검색 끝 ===== ");
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	// 내 그룹 리스트
	@GetMapping("/my-list")
	ResponseEntity<?> listMyGroup(@RequestParam String userNickname) throws Exception {
		log.info("===== 내 그룹 리스트 시작===== ");

		List<GroupDTO> groupDTOList = groupService.getMyGroupList(userNickname);
		log.info("내 그룹 정보");
		log.info(groupDTOList.toString());

		log.info("===== 내 그룹 리스트 끝===== ");
		return new ResponseEntity<>(groupDTOList, HttpStatus.OK);

	}

	// 참여 가능 그룹 리스트
	@GetMapping("/joinable-list")
	ResponseEntity<?> listJoinable(@RequestParam String userNickname) throws Exception {
		log.info("===== 참여 가능한 그룹 리스트 시작===== ");
		List<GroupDTO> groupDTOList = groupService.getJoinableList(userNickname);
		
		log.info("참여 가능한 그룹 목록");
		log.info(" "+groupDTOList.toString());

		log.info("===== 참여 가능한 그룹 리스트 끝===== ");
		return new ResponseEntity<>(groupDTOList, HttpStatus.OK);
	}

	// 그룹 상세
	@GetMapping("/details")
	ResponseEntity<?> getDetails(@RequestParam Long simulationSeq, @RequestParam String progressState) throws
		Exception {
		log.info("===== 그룹 상세 시작===== ");

		// 대기중인 그룹 - 상세
		if (progressState.equals("waiting")) {
			log.info("===== 시작 전인 그룹 상세 끝 ===== ");
			return ResponseEntity.ok(groupService.getWaitingGroupDetails(simulationSeq));
		}
		// 진행중인 그룹 - 상세
		else if (progressState.equals("inProgress")) {
			log.info("===== 진행 중인 상세 끝  ===== ");
			return ResponseEntity.ok(groupService.getInProgressGroupDetails(simulationSeq));
		}
		// 해당하는 그룹이 없을 때
		else {
			throw new RuntimeException(simulationSeq+"번이 "+progressState+"하는 시뮬레이션이 존재하지 않습니다.");
		}
	}

	// 그룹 참여하기
	@PostMapping("/join")
	ResponseEntity<?> joinGroup(@RequestBody GroupUserDTO groupUserDTO) throws Exception {
		log.info("===== 그룹 참여하기 시작  ===== ");
		log.info("입력 파라미터 "+groupUserDTO);

		groupService.joinGroup(groupUserDTO.getSimulationSeq(), groupUserDTO.getUserSeq());

		log.info("===== 그룹 참여하기 끝  ===== ");
		return ResponseEntity.ok().build();
	}
	// 그룹 나가기 | 방장 나가기
	// 모의 투자 시작
}
