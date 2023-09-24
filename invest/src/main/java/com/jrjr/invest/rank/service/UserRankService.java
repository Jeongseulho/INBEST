package com.jrjr.invest.rank.service;

import java.util.Set;

import com.jrjr.invest.rank.dto.RedisUserDTO;

public interface UserRankService {

	void insertUserInfo(RedisUserDTO redisUserDTO);

	void updateUserProfileInfo(RedisUserDTO redisUserDTO);

	void updateUserTierAndRateInfo(Long seq);

	void updateAllUserTierAndRateInfo();

	void updateUserRankingInfo();

	Set<RedisUserDTO> getUserRankingInfo(long start, long end);

	void printUserInfoList();

	void printSortedUserInfoList(long start, long end);
}
