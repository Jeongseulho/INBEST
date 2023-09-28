package com.jrjr.invest.rank.service;

import java.util.List;

import com.jrjr.invest.rank.dto.RedisTierRankDTO;
import com.jrjr.invest.rank.dto.RedisUserDTO;

public interface UserRankService {

	void insertUserInfo(RedisUserDTO redisUserDto); // 회원 정보 추가

	void updateUserProfileInfo(RedisUserDTO redisUserDto); // 회원 프로필 정보 수정

	void deleteUserInfo(Long seq); // 회원 정보 삭제

	void updateUserTierAndRateInfo(Long seq); // 회원 티어 및 수익률 정보 업데이트

	void updateAllUserTierAndRateInfo(); // 전체 회원 티어 및 수익률 정보 업데이트

	void updateUserRankingInfo(); // 개인 랭킹 산정

	List<RedisUserDTO> getUserRankingInfo(Long start, Long end); // 전체 개인 랭킹 정보 불러오기

	RedisUserDTO getUserRankingInfo(Long seq); // 내 개인 랭킹 정보 불러오기

	void updateTierDistributionChartInfo(); // 티어 분포 산정

	RedisTierRankDTO getTierDistributionChartInfo(); // 티어 분포도 정보 불러오기
}
