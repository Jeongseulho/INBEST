package com.jrjr.invest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invest")
public class TestController {

	@GetMapping()
	public String test() {
		return "성공!";
	}
}
