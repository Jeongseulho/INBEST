package com.jrjr.invest.rank.service;

import org.springframework.stereotype.Service;

import com.jrjr.invest.rank.dto.RedisUserDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RankServiceImpl implements RankService {

	@Override
	public void insertUserRankingInfo(RedisUserDTO redisUserDTO) {
		log.info("회원 정보: {}", redisUserDTO.toString());

	}

	@Override
	public void updateUserRankingInfo(RedisUserDTO redisUserDTO) {
		log.info("회원 정보: {}", redisUserDTO.toString());
	}
}
