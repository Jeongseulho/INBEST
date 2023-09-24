package com.jrjr.invest.rank.repository;

import java.util.Map;
import java.util.Set;

import com.jrjr.invest.rank.dto.RedisUserDTO;

public interface UserRepository {

	RedisUserDTO getUserInfo(Long seq);

	Map<String, RedisUserDTO> getUserInfoMap();

	Set<String> getAllHashKeys();

	Set<RedisUserDTO> getUserInfoSet(long start, long end);

	void insertUserInfo(RedisUserDTO redisUserDTO);

	void updateUserProfileInfo(RedisUserDTO inputRedisUserDTO);

	void deleteUserInfo(Long seq);

	void updateUserTierAndRateInfo(Long seq, Integer tier, Integer rate);

	void removeAllFromSortedUserSet();

	void updateUserRankingList();

	RedisUserDTO getMyRankingInfo(Long seq);
}
