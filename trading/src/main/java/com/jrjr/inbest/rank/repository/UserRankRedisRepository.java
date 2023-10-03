package com.jrjr.inbest.rank.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import com.jrjr.inbest.rank.dto.RedisTierRankDTO;
import com.jrjr.inbest.rank.dto.RedisUserDTO;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class UserRankRedisRepository {

	private final HashOperations<String, String, RedisUserDTO> userHash; // key: USER_HASH_KEY
	private final HashOperations<String, String, RedisTierRankDTO> tierRankHash; // key: TIER_RANK_HASH_KEY
	private final ZSetOperations<String, String> userZSet; // key: USER_SORT_KEY

	static final String USER_HASH_KEY = "user";
	static final String TIER_RANK_HASH_KEY = "tier_rank";
	static final String USER_SORT_KEY = "user_sort";

	@Autowired
	public UserRankRedisRepository(RedisTemplate<String, RedisUserDTO> userRedisTemplate,
		RedisTemplate<String, RedisTierRankDTO> tierRankRedisTemplate,
		RedisTemplate<String, String> stringStringRedisTemplate) {
		this.userHash = userRedisTemplate.opsForHash();
		this.userZSet = stringStringRedisTemplate.opsForZSet();
		this.tierRankHash = tierRankRedisTemplate.opsForHash();
	}

	/*
		userHash 삭제
	 */
	public void deleteAllUserInfo() {
		Set<String> allHashKeys = this.getAllHashKeys();
		for (String userSeq : allHashKeys) {
			userHash.delete(USER_HASH_KEY, userSeq);
		}
		log.info("userHash 초기화 완료");
		userZSet.removeRange(USER_SORT_KEY, 0, -1);
		log.info("userZSet 초기화 완료");
	}

	/*
		내 회원 정보 불러오기
	 */
	public RedisUserDTO getUserInfo(Long seq) {
		return userHash.get(USER_HASH_KEY, String.valueOf(seq));
	}

	/*
		전체 회원 정보 불러오기
	 */
	public Map<String, RedisUserDTO> getUserInfoMap() {
		return userHash.entries(USER_HASH_KEY);
	}

	/*
		Redis user hash table 의 모든 key 값 가져오기 (userSeq 목록)
	 */
	public Set<String> getAllHashKeys() {
		return userHash.keys(USER_HASH_KEY);
	}

	/*
		userZSet 불러오기
	 */
	public Set<String> getUserRankingInfoSet(Long start, Long end) {
		return userZSet.reverseRange(USER_SORT_KEY, start, end);
	}

	/*
		전체 개인 랭킹 정보 불러오기
	 */
	public List<RedisUserDTO> getUserRankingInfoList(Long start, Long end) {
		List<RedisUserDTO> redisUserDtos = new ArrayList<>();
		Set<String> userRankingInfoSet = this.getUserRankingInfoSet(start, end);
		for (String userSeq : userRankingInfoSet) {
			redisUserDtos.add(this.getUserInfo(Long.parseLong(userSeq)));
		}
		return redisUserDtos;
	}

	/*
		내 개인 랭킹 정보 불러오기
	 */
	public RedisUserDTO getUserRankingInfo(Long userSeq) {
		return this.getUserInfo(userSeq);
	}

	/*
		회원 정보 추가
	 */
	public void insertUserInfo(RedisUserDTO redisUserDto) {
		redisUserDto.setTier(0);
		redisUserDto.setRate(0);
		userHash.put(USER_HASH_KEY, String.valueOf(redisUserDto.getSeq()), redisUserDto);
		userZSet.add(USER_SORT_KEY, String.valueOf(redisUserDto.getSeq()), 0);
	}

	/*
		회원 프로필 정보 수정
	 */
	public void updateUserProfileInfo(RedisUserDTO inputRedisUserDto) {
		RedisUserDTO redisUserDto = userHash.get(USER_HASH_KEY, String.valueOf(inputRedisUserDto.getSeq()));
		if (redisUserDto == null) {
			log.info("updateUserProfileInfo: 해당 회원 정보가 없음");
			return;
		}
		log.info("변경 전 회원 정보: {}", redisUserDto);

		redisUserDto.setNickname(inputRedisUserDto.getNickname());
		redisUserDto.setProfileImgSearchName(inputRedisUserDto.getProfileImgSearchName());

		userHash.put(USER_HASH_KEY, String.valueOf(redisUserDto.getSeq()), redisUserDto);
		log.info("변경 후 회원 정보: {}", redisUserDto);
	}

	/*
		회원 정보 삭제
	 */
	public void deleteUserInfo(Long seq) {
		userHash.delete(USER_HASH_KEY, String.valueOf(seq));
		userZSet.remove(USER_SORT_KEY, String.valueOf(seq));
	}

	/*
		회원 티어 및 수익률 정보 업데이트
	 */
	public void updateUserTierAndRateInfo(Long seq, Integer tier, Integer rate) {
		RedisUserDTO redisUserDto = this.getUserInfo(seq);
		log.info("변경 전 회원 정보: {}", redisUserDto.toString());
		redisUserDto.setTier(tier);
		redisUserDto.setRate(rate);

		userHash.put(USER_HASH_KEY, String.valueOf(seq), redisUserDto);
		userZSet.add(USER_SORT_KEY, String.valueOf(seq), tier);
		log.info("변경 후 회원 정보: {}", redisUserDto);
	}

	/*
		개인 랭킹 산정
	 */
	public void updateUserRankingList() {
		int rank = 0;
		int index = 0;
		int previousTier = Integer.MAX_VALUE;

		Set<String> userRankingInfoSet = this.getUserRankingInfoSet(0L, -1L);

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		for (String userSeq : userRankingInfoSet) {
			index++;
			int tier = userZSet.score(USER_SORT_KEY, userSeq).intValue();

			if (tier != previousTier) {
				rank = index;
			}
			previousTier = tier;

			RedisUserDTO redisUserDto = this.getUserInfo(Long.parseLong(userSeq));
			redisUserDto.setPreviousRank(redisUserDto.getCurrentRank());
			redisUserDto.setCurrentRank(rank);
			userHash.put(USER_HASH_KEY, String.valueOf(redisUserDto.getSeq()), redisUserDto);
		}

		stopWatch.stop();
		log.info("랭킹 재산정 소요 시간: {} milliseconds", stopWatch.getTotalTimeMillis());
	}

	/*
		티어 분포 산정
	 */
	public void updateTierDistributionChartInfo() {
		int bronze = 0;
		int silver = 0;
		int gold = 0;
		int diamond = 0;
		Map<String, RedisUserDTO> userDtoMap = this.getUserInfoMap();
		for (RedisUserDTO redisUserDto : userDtoMap.values()) {
			int tier = redisUserDto.getTier();
			if (tier < 100) { // 브론즈: 0~99
				bronze++;
			} else if (tier < 200) { // 실버: 100~199
				silver++;
			} else if (tier < 300) { // 골드: 200~299
				gold++;
			} else { // 다이아: 300 이상
				diamond++;
			}
		}

		RedisTierRankDTO redisTierRankDto
			= RedisTierRankDTO.builder()
			.bronze(bronze).silver(silver).gold(gold).diamond(diamond)
			.build();

		tierRankHash.put(TIER_RANK_HASH_KEY, "rank", redisTierRankDto);
	}

	/*
		티어 분포도 정보 불러오기
	 */
	public RedisTierRankDTO getTierDistributionChartInfo() {
		RedisTierRankDTO redisTierRankDto = tierRankHash.get(TIER_RANK_HASH_KEY, "rank");
		if (redisTierRankDto == null) {
			log.info("getTierRankList: 티어 분포도 정보가 없음");
			return null;
		}
		log.info(redisTierRankDto.toString());
		return redisTierRankDto;
	}
}
