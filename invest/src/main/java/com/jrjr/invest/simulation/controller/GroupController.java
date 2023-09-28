package com.jrjr.invest.simulation.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
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
import com.jrjr.invest.simulation.dto.SimulationSeqDTO;
import com.jrjr.invest.simulation.dto.StatusDTO;
import com.jrjr.invest.simulation.dto.UserDTO;
import com.jrjr.invest.simulation.service.GroupService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/group")
@RestController
@RequiredArgsConstructor
@Slf4j
public class GroupController {
	private final GroupService groupService;
	// 그룹 생성
	@Operation(summary = "모의 투자방 생성")
	@PostMapping()
	ResponseEntity<?> createGroup(@RequestBody CreatedGroupDTO groupDTO) throws Exception {
		log.info("[그룹 생성 시작]");
		log.info("[입력 파라미터 : " + groupDTO + " ]");

		groupService.createGroup(groupDTO);

		log.info("[그룹 생성 끝]");
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// 그룹 생성 시, 초대를 위한 유저 목록 검색
	@Operation(summary = "유저 검색")
	@Parameters(value = {
		@Parameter(description = "검색어" , name="keyword")
	})
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

	@GetMapping("/status")
	ResponseEntity<?> searchStatus() throws UnsupportedEncodingException {
		log.info("===== 모의투자 상태 검색 시작 =====");

		StatusDTO statusDTO = StatusDTO.builder().build();

		statusDTO.setTotalUserNum(groupService.getTotalUserNum());
		statusDTO.setTotalUserNumFluctuation(groupService.getTotalUserNumFluctuation());
		statusDTO.setCurrentUserNum(groupService.getCurrentUserNum());
		statusDTO.setCurrentUserNumFluctuation(groupService.getCurrentUserNumFluctuation());
		statusDTO.setInprogressUserNum(groupService.getInprogressUserNum());
		statusDTO.setInprogressUserNumFluctuation(groupService.getTotalUserNumFluctuation());
		statusDTO.setInprogressGroupNum(groupService.getInprogressGroupNum());
		statusDTO.setInprogressGroupNumFluctuation(groupService.getInprogressGroupNumFluctuation());
		statusDTO.setFinishedGroupNum(groupService.getFinishedGroupNum());
		statusDTO.setFinishedGroupNumFluctuation(groupService.getFinishedGroupNumFluctuation());
		statusDTO.setRevenueRateFluctuation(groupService.getRevenueRateFluctuation());
		statusDTO.setRevenueRate(groupService.getRevenueRate());

		log.info("===== 모의투자 상태 검색 끝 ===== ");
		return new ResponseEntity<>(statusDTO, HttpStatus.OK);
	}
	@Operation(summary = "유저가 참가한 그룹 목록")
	@Parameters(value = {
		@Parameter(description = "유저 닉네임" , name="userNickname",required = true)
	})
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
	@Operation(summary = "참여 가능한 그룹 목록")
	@Parameters(value = {
		@Parameter(description = "유저 닉네임" , name="userNickname",required = true)
	})
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
	@Operation(summary = "그룹 상세")
	@Parameters(value = {
		@Parameter(description = "모의 투자방 pk" , name="simulationSeq",required = true),
		@Parameter(description = "투자방 진행상황" , name="progressState",required = true)
	})
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
	@Operation(summary = "그룹 참여하기")
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
	// 그룹 참여하기
	// @PostMapping("/exit")
	// ResponseEntity<?> exitGroup(@RequestBody GroupUserDTO groupUserDTO) throws Exception {
	// 	log.info("===== 그룹 나가기 시작  ===== ");
	// 	log.info("입력 파라미터 "+groupUserDTO);
	//
	// 	groupService.joinGroup(groupUserDTO.getSimulationSeq(), groupUserDTO.getUserSeq());
	//
	// 	log.info("===== 그룹 나가기 끝  ===== ");
	// 	return ResponseEntity.ok().build();
	// }
	// 모의 투자 시작
	@Operation(summary = "그룹 시작하기")
	@Parameters(value = {
		@Parameter(description = "로그인한 유저 pk(실제 사용 시 자동으로 엑세스 토큰에서 가져오므로 안넣어도 괜찮음)"
			,name="loginSeq",required = true),
	})
	@PostMapping("/exit")
	ResponseEntity<?> exitGroup(@RequestBody GroupUserDTO groupUserDTO,
		@RequestParam(required = false, defaultValue = "",name="loginSeq") String loginSeq
	) throws Exception {
		log.info("===== 그룹 나가기 시작  ===== ");
		log.info("입력 파라미터: "+groupUserDTO);
		log.info("로그인 유저: "+loginSeq);
	
		//비로그인 처리
		if(loginSeq.equals("")){
			throw new Exception("로그인하지 않은 유저입니다.");
		}else if(Long.valueOf(loginSeq) != groupUserDTO.getUserSeq()){
			throw new Exception(loginSeq+"로그인 유저는 "+groupUserDTO.getUserSeq()+"유저가 아닙니다.");
		}

		groupService.leaveGroup(groupUserDTO.getSimulationSeq(),groupUserDTO.getUserSeq());

		log.info("===== 그룹 나가기 끝  ===== ");
		return ResponseEntity.ok().build();
	}
}
