package com.jrjr.invest.rank.service;

import com.jrjr.invest.rank.dto.RedisUserDTO;

public interface RankService {

	void insertUserRanking(RedisUserDTO redisUserDTO);

	void sortUserRanking();

	void updateUserRankingProfileInfo(RedisUserDTO redisUserDTO);

	void updateUserRankingTierAndRateInfo(Long seq);
}
