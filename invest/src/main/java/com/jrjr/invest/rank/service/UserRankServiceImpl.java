package com.jrjr.invest.rank.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.jrjr.invest.rank.dto.RedisTierRankDTO;
import com.jrjr.invest.rank.dto.RedisUserDTO;
import com.jrjr.invest.rank.repository.UserRankRedisRepository;
import com.jrjr.invest.simulation.repository.RateRepository;
import com.jrjr.invest.simulation.repository.TierRepository;
import com.jrjr.invest.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserRankServiceImpl implements UserRankService {

	private final UserRepository userRepository;
	private final UserRankRedisRepository userRankRedisRepository;
	private final TierRepository tierRepository;
	private final RateRepository rateRepository;

	/*
		회원 정보 추가
	 */
	@Override
	public void insertUserInfo(RedisUserDTO redisUserDto) {
		log.info("추가 할 회원 정보: {}", redisUserDto.toString());

		userRankRedisRepository.insertUserInfo(redisUserDto);
		log.info("회원 정보 추가 완료");

		this.updateUserRankingInfo();
	}

	/*
		전체 회원 정보 초기화 후 추가
	 */
	@Override
	public void insertAllUserInfo() {
		userRankRedisRepository.deleteAllUserInfo();
		log.info("전체 회원 정보 삭제 완료");
		List<RedisUserDTO> redisUsers = userRepository.getUserInfo();
		log.info("전체 회원 정보 조회 완료");
		for (RedisUserDTO redisUserDto : redisUsers) {
			userRankRedisRepository.insertUserInfo(redisUserDto);
		}
		log.info("전체 회원 정보 추가 완료");
	}

	/*
			회원 프로필 정보 수정
		 */
	@Override
	public void updateUserProfileInfo(RedisUserDTO redisUserDto) {
		log.info("수정 할 회원 정보: {}", redisUserDto.toString());

		userRankRedisRepository.updateUserProfileInfo(redisUserDto);
		this.updateUserRankingInfo();
	}

	/*
		회원 정보 삭제
	 */
	@Override
	public void deleteUserInfo(Long seq) {
		log.info("삭제 할 회원 정보 seq: {}", seq);

		userRankRedisRepository.deleteUserInfo(seq);
		this.updateUserRankingInfo();
	}

	/*
		회원 티어 및 수익률 정보 업데이트
	 */
	@Override
	public void updateUserTierAndRateInfo(Long userSeq) {
		log.info("변경 요청 회원 pk: {}", userSeq);

		// 티어 계산
		Integer totalTier = tierRepository.getTotalTierByUserSeq(userSeq);

		if (totalTier == null) {
			totalTier = 0;
		}

		// 티어 >= 0 (음수 일 경우 0으로 변경)
		if (totalTier < 0) {
			totalTier = 0;
		}
		
		log.info("totalTier: " + totalTier);

		// 평균 수익률 계산
		Integer avgRate = rateRepository.getAvgRateByUserSeq(userSeq);

		if (avgRate == null) {
			avgRate = 0;
		}

		log.info("avgRate: " + avgRate);

		userRankRedisRepository.updateUserTierAndRateInfo(userSeq, totalTier, avgRate);
	}

	/*
		전체 회원 티어 및 수익률 정보 업데이트
	 */
	@Override
	public void updateAllUserTierAndRateInfo() {
		Set<String> hashKeys = userRankRedisRepository.getAllHashKeys();
		for (String userSeq : hashKeys) {
			this.updateUserTierAndRateInfo(Long.parseLong(userSeq));
		}
	}

	/*
		개인 랭킹 산정
	 */
	@Override
	public void updateUserRankingInfo() {
		userRankRedisRepository.updateUserRankingList();
	}

	/*
		전체 개인 랭킹 정보 불러오기
	 */
	@Override
	public List<RedisUserDTO> getUserRankingInfo(Long start, Long end) {
		return userRankRedisRepository.getUserRankingInfoList(start, end);
	}

	/*
		내 개인 랭킹 정보 불러오기
	 */
	@Override
	public RedisUserDTO getUserRankingInfo(Long seq) {
		return userRankRedisRepository.getUserRankingInfo(seq);
	}

	/*
		티어 분포 산정
	 */
	@Override
	public void updateTierDistributionChartInfo() {
		userRankRedisRepository.updateTierDistributionChartInfo();
	}

	/*
		티어 분포도 정보 불러오기
	 */
	@Override
	public RedisTierRankDTO getTierDistributionChartInfo() {
		return userRankRedisRepository.getTierDistributionChartInfo();
	}
}
