package com.jrjr.invest.rank.service;

import java.util.List;
import java.util.Map;
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

	@Override
	public void insertUserInfo(RedisUserDTO redisUserDto) {
		log.info("추가 할 회원 정보: {}", redisUserDto.toString());

		userRankRedisRepository.insertUserInfo(redisUserDto);
		log.info("회원 정보 추가 완료");

		this.updateUserRankingInfo();
	}

	@Override
	public void updateUserProfileInfo(RedisUserDTO redisUserDto) {
		log.info("수정 할 회원 정보: {}", redisUserDto.toString());

		userRankRedisRepository.updateUserProfileInfo(redisUserDto);
		this.updateUserRankingInfo();
	}

	@Override
	public void deleteUserInfo(Long seq) {
		log.info("삭제 할 회원 정보 seq: {}", seq);

		userRankRedisRepository.deleteUserInfo(seq);
		this.updateUserRankingInfo();
	}

	@Override
	public void updateUserTierAndRateInfo(Long seq) {
		log.info("변경 요청 회원 pk: {}", seq);

		// 경험치 계산
		List<Tier> tiers = tierRepository.findAllByUserSeq(seq);
		Integer totalTier = 0;
		for (Tier tier : tiers) {
			log.info(tier.toString());
			totalTier += tier.getTier();
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

	@Override
	public void updateAllUserTierAndRateInfo() {
		Set<String> hashKeys = userRankRedisRepository.getAllHashKeys();
		for (String seq : hashKeys) {
			this.updateUserTierAndRateInfo(Long.parseLong(seq));
		}
	}

	@Override
	public void updateUserRankingInfo() {
		userRankRedisRepository.updateUserRankingList();
	}

	@Override
	public Set<RedisUserDTO> getUserRankingInfo(long start, long end) {
		return userRankRedisRepository.getUserInfoSet(start, end);
	}

	@Override
	public RedisUserDTO getMyRankingInfo(Long seq) {
		return userRankRedisRepository.getMyRankingInfo(seq);
	}

	@Override
	public void updateTierRankInfo() {
		userRankRedisRepository.updateTierRankList();
	}

	@Override
	public RedisTierRankDTO getTierRankInfo() {
		return userRankRedisRepository.getTierRankList();
	}

	@Override
	public void printUserInfoList() {
		log.info("========== 회원 정보 ==========");
		Map<String, RedisUserDTO> userDtoMap = userRankRedisRepository.getUserInfoMap();
		for (String seq : userDtoMap.keySet()) {
			log.info(userDtoMap.get(seq).toString());
		}
		log.info("================================");
	}

	@Override
	public void printSortedUserInfoList(long start, long end) {
		log.info("========== 정렬된 회원 랭킹 정보 ==========");
		Set<RedisUserDTO> sortedUserSet = userRankRedisRepository.getUserInfoSet(start, end);
		for (RedisUserDTO redisUserDto : sortedUserSet) {
			log.info(redisUserDto.toString());
		}
		log.info("================================");
	}
}
