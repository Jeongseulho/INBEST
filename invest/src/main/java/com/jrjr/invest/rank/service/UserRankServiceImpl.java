package com.jrjr.invest.rank.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.jrjr.invest.rank.dto.RedisTierRankDTO;
import com.jrjr.invest.rank.dto.RedisUserDTO;
import com.jrjr.invest.rank.repository.UserRankRedisRepository;
import com.jrjr.invest.simulation.entity.Rate;
import com.jrjr.invest.simulation.entity.Tier;
import com.jrjr.invest.simulation.repository.RateRepository;
import com.jrjr.invest.simulation.repository.TierRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserRankServiceImpl implements UserRankService {

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
	public void updateUserTierAndRateInfo(Long seq) {
		log.info("변경 요청 회원 pk: {}", seq);

		// 티어 계산
		List<Tier> tiers = tierRepository.findAllByUserSeq(seq);
		Integer totalTier = 0;
		for (Tier tier : tiers) {
			log.info(tier.toString());
			totalTier += tier.getTier();
		}

		// 티어 >= 0 (음수 일 경우 0으로 변경)
		if (totalTier < 0) {
			totalTier = 0;
		}
		log.info("totalTier: " + totalTier);

		// 평균 수익률 계산
		List<Rate> rates = rateRepository.findAllByUserSeq(seq);
		Integer totalRate = 0;
		for (Rate rate : rates) {
			log.info(rate.toString());
			totalRate += rate.getRate();
		}
		log.info("totalRate: " + totalRate);
		Integer avgRate = (int)Math.round(totalRate * 1.0 / rates.size());
		log.info("avgRate: " + avgRate);

		userRankRedisRepository.updateUserTierAndRateInfo(seq, totalTier, avgRate);
	}

	/*
		전체 회원 티어 및 수익률 정보 업데이트
	 */
	@Override
	public void updateAllUserTierAndRateInfo() {
		Set<String> hashKeys = userRankRedisRepository.getAllHashKeys();
		for (String seq : hashKeys) {
			this.updateUserTierAndRateInfo(Long.parseLong(seq));
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
