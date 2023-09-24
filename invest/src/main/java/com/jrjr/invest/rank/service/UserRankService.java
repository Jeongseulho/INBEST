package com.jrjr.invest.rank.service;

import com.jrjr.invest.rank.dto.RedisUserDTO;

public interface UserRankService {

	void insertUserInfo(RedisUserDTO redisUserDTO);

	void updateUserProfileInfo(RedisUserDTO redisUserDTO);

	void updateUserTierAndRateInfo(Long seq);

	void sortUserRankingInfo();

	void printUserInfoList();

	void printSortedUserInfoList(long start, long end);
}
