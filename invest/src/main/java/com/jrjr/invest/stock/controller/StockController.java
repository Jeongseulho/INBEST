package com.jrjr.invest.stock.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stocks")
public class StockController {

	@GetMapping("/buy")
	public String buyStock() {


		return "성공!";
	}
	@GetMapping("/sell")
	public String sellStock() {
		return "성공!";
	}
}
