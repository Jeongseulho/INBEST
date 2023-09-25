package com.jrjr.invest.rank.service;

import java.util.Set;

import com.jrjr.invest.rank.dto.RedisTierRankDTO;
import com.jrjr.invest.rank.dto.RedisUserDTO;

public interface UserRankService {

	void insertUserInfo(RedisUserDTO redisUserDto);

	void updateUserProfileInfo(RedisUserDTO redisUserDto);

	void deleteUserInfo(Long seq);

	void updateUserTierAndRateInfo(Long seq);

	void updateAllUserTierAndRateInfo();

	void updateUserRankingInfo();

	Set<RedisUserDTO> getUserRankingInfo(long start, long end);

	RedisUserDTO getMyRankingInfo(Long seq);

	void updateTierRankInfo();

	RedisTierRankDTO getTierRankInfo();

	void printUserInfoList();

	void printSortedUserInfoList(long start, long end);
}
