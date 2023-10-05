package com.jrjr.invest.simulation.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jrjr.invest.simulation.dto.AssetDTO;
import com.jrjr.invest.simulation.dto.ResponseUserStockDTO;
import com.jrjr.invest.simulation.dto.SearchByTitleDTO;
import com.jrjr.invest.simulation.dto.SimulationSeqDTO;
import com.jrjr.invest.simulation.dto.StatusDTO;
import com.jrjr.invest.simulation.dto.UserDTO;
import com.jrjr.invest.simulation.dto.group.CreatedGroupDTO;
import com.jrjr.invest.simulation.dto.group.GroupDTO;
import com.jrjr.invest.simulation.dto.group.GroupUserDTO;
import com.jrjr.invest.simulation.entity.Simulation;
import com.jrjr.invest.simulation.service.GroupService;
import com.jrjr.invest.simulation.service.SimulationService;
import com.jrjr.invest.trading.dto.ResponseTradingDTO;
import com.jrjr.invest.trading.service.TradingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/simulation")
@RestController
@RequiredArgsConstructor
@Slf4j
public class SimulationController {
	private final SimulationService simulationService;

	// 그룹 생성
	@Operation(summary = "모의 투자방 나가기")
	@GetMapping("/{simulationSeq}/exit")
	ResponseEntity<?> createGroup(
		@PathVariable(name = "simulationSeq")Long simulationSeq,
		@RequestParam(name="loginSeq") Long loginSeq) throws Exception {
		log.info("[진행중 모의 투자 나가기 시작]");

		simulationService.leaveSimulation(simulationSeq,loginSeq);

		log.info("[진행중 모의 투자 나가기 끝]");
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
