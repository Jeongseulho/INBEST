package com.jrjr.invest.rank.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.jrjr.invest.rank.dto.RedisUserDTO;
import com.jrjr.invest.simulation.entity.Rate;
import com.jrjr.invest.simulation.entity.Tier;
import com.jrjr.invest.simulation.repository.RateRepository;
import com.jrjr.invest.simulation.repository.TierRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RankServiceImpl implements RankService {

	private final RedisTemplate<String, RedisUserDTO> redisUserDtoRedisTemplate;
	private final TierRepository tierRepository;
	private final RateRepository rateRepository;

	static final String USER_HASH_KEY = "user";

	@Override
	public void insertUserRanking(RedisUserDTO redisUserDTO) {
		log.info("회원 정보: {}", redisUserDTO.toString());

		HashOperations<String, String, RedisUserDTO> redisUserDTOHashOperations = redisUserDtoRedisTemplate.opsForHash();
		redisUserDTOHashOperations.put(USER_HASH_KEY, String.valueOf(redisUserDTO.getSeq()), redisUserDTO);

		Map<String, RedisUserDTO> redisUserDTOMap = redisUserDTOHashOperations.entries(USER_HASH_KEY);
		for (String seq : redisUserDTOMap.keySet()) {
			log.info(redisUserDTOMap.get(seq).toString());
		}
	}

	@Override
	public void updateUserRankingProfileInfo(RedisUserDTO inputRedisUserDTO) {
		log.info("변경 요청 회원 정보: {}", inputRedisUserDTO.toString());

		HashOperations<String, String, RedisUserDTO> redisUserDTOHashOperations = redisUserDtoRedisTemplate.opsForHash();
		RedisUserDTO redisUserDTO = redisUserDTOHashOperations.get(USER_HASH_KEY,
			String.valueOf(inputRedisUserDTO.getSeq()));
		log.info("변경 전 회원 정보: {}", redisUserDTO.toString());

		redisUserDTO.setNickname(inputRedisUserDTO.getNickname());
		redisUserDTO.setProfileImgSearchName(inputRedisUserDTO.getProfileImgSearchName());

		redisUserDTOHashOperations.put(USER_HASH_KEY, String.valueOf(redisUserDTO.getSeq()), redisUserDTO);
		log.info("변경 후 회원 정보: {}", redisUserDTO);
	}

	@Override
	public void sortUserRanking() {

	}

	@Override
	public void updateUserRankingTierAndRateInfo(Long seq) {
		log.info("변경 요청 회원 pk: {}", seq);

		HashOperations<String, String, RedisUserDTO> redisUserDTOHashOperations = redisUserDtoRedisTemplate.opsForHash();
		RedisUserDTO redisUserDTO = redisUserDTOHashOperations.get(USER_HASH_KEY, String.valueOf(seq));
		log.info("변경 전 회원 정보: {}", redisUserDTO.toString());

		List<Tier> tiers = tierRepository.findAllByUserSeq(seq);
		Integer totalTier = 0;
		for (Tier tier : tiers) {
			log.info(tier.toString());
			totalTier += tier.getTier();
		}
		log.info("totalTier: " + totalTier);
		redisUserDTO.setTier(totalTier);

		List<Rate> rates = rateRepository.findAllByUserSeq(seq);
		Integer totalRate = 0;
		for (Rate rate : rates) {
			log.info(rate.toString());
			totalRate += rate.getRate();
		}
		log.info("totalRate: " + totalRate);
		Integer avgRate = (int)Math.round(totalRate * 1.0 / rates.size());
		log.info("avgRate: " + avgRate);
		redisUserDTO.setRate(avgRate);

		redisUserDTOHashOperations.put(USER_HASH_KEY, String.valueOf(seq), redisUserDTO);
		log.info("변경 후 회원 정보: {}", redisUserDTO);
	}
}
