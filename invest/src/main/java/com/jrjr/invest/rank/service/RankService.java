package com.jrjr.invest.rank.service;

import com.jrjr.invest.rank.dto.RedisUserDTO;

public interface RankService {

	void insertUserRankingInfo(RedisUserDTO redisUserDTO);

	void updateUserRankingInfo(RedisUserDTO redisUserDTO);
}
