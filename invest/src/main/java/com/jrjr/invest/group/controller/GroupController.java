package com.jrjr.invest.group.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jrjr.invest.group.dto.SimulationDTO;
import com.jrjr.invest.group.service.GroupService;
import com.jrjr.invest.simulation.entity.Simulation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/group")
@RestController
@RequiredArgsConstructor
@Slf4j
public class GroupController {

	private final GroupService groupService;

	@PostMapping("/")
	ResponseEntity<?> createGroup(@RequestBody SimulationDTO simulationDTO) {
		// db 생성

		// 방장 redis 생성
	}

	@GetMapping("/")
	ResponseEntity<?> searchUsers(@RequestParam String keyword) {
		groupService.searchUsers(keyword);
	}
}
