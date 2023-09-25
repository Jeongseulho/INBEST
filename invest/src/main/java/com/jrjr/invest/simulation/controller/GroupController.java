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


	@PostMapping("/")
	ResponseEntity<?> createGroup(@RequestBody GroupDTO groupDTO) throws Exception {
		groupService.createGroup(groupDTO);
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@GetMapping("/")
	ResponseEntity<?> searchUsers(@RequestParam String keyword) {
		List<UserDTO> users = groupService.searchUsers(keyword);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
}
