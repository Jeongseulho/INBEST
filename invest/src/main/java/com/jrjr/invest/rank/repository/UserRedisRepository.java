package com.jrjr.invest.rank.repository;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import com.jrjr.invest.rank.dto.RedisTierRankDTO;
import com.jrjr.invest.rank.dto.RedisUserDTO;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class UserRedisRepository implements UserRepository {

	private final HashOperations<String, String, RedisUserDTO> userHashOperations;
	private final HashOperations<String, String, RedisTierRankDTO> tierRankHashOperations;
	private final ZSetOperations<String, RedisUserDTO> userZSetOperations;

	static final String USER_HASH_KEY = "user";
	static final String TIER_RANK_HASH_KEY = "tier-rank";
	static final String USER_SORT_KEY = "user-sort";

	@Autowired
	public UserRedisRepository(RedisTemplate<String, RedisUserDTO> userRedisTemplate,
		RedisTemplate<String, RedisTierRankDTO> tierRankRedisTemplate) {
		this.userHashOperations = userRedisTemplate.opsForHash();
		this.userZSetOperations = userRedisTemplate.opsForZSet();
		this.tierRankHashOperations = tierRankRedisTemplate.opsForHash();
	}

	@Override
	public RedisUserDTO getUserInfo(Long seq) {
		return userHashOperations.get(USER_HASH_KEY, String.valueOf(seq));
	}

	@Override
	public Map<String, RedisUserDTO> getUserInfoMap() {
		return userHashOperations.entries(USER_HASH_KEY);
	}

	@Override
	public Set<String> getAllHashKeys() {
		return userHashOperations.keys(USER_HASH_KEY);
	}

	@Override
	public Set<RedisUserDTO> getUserInfoSet(long start, long end) {
		return userZSetOperations.reverseRange(USER_SORT_KEY, start, end);
	}

	@Override
	public void insertUserInfo(RedisUserDTO redisUserDto) {
		redisUserDto.setTier(0);
		redisUserDto.setRate(0);
		userHashOperations.put(USER_HASH_KEY, String.valueOf(redisUserDto.getSeq()), redisUserDto);
	}

	@Override
	public void updateUserProfileInfo(RedisUserDTO inputRedisUserDto) {
		RedisUserDTO redisUserDto = userHashOperations.get(USER_HASH_KEY, String.valueOf(inputRedisUserDto.getSeq()));
		log.info("변경 전 회원 정보: {}", redisUserDto.toString());

		redisUserDto.setNickname(inputRedisUserDto.getNickname());
		redisUserDto.setProfileImgSearchName(inputRedisUserDto.getProfileImgSearchName());

		userHashOperations.put(USER_HASH_KEY, String.valueOf(redisUserDto.getSeq()), redisUserDto);
		log.info("변경 후 회원 정보: {}", redisUserDto);
	}

	@Override
	public void deleteUserInfo(Long seq) {
		userHashOperations.delete(USER_HASH_KEY, String.valueOf(seq));
	}

	@Override
	public void updateUserTierAndRateInfo(Long seq, Integer tier, Integer rate) {
		RedisUserDTO redisUserDto = this.getUserInfo(seq);
		log.info("변경 전 회원 정보: {}", redisUserDto.toString());

		redisUserDto.setTier(tier);
		redisUserDto.setRate(rate);

		userHashOperations.put(USER_HASH_KEY, String.valueOf(seq), redisUserDto);
		log.info("변경 후 회원 정보: {}", redisUserDto);
	}

	@Override
	public void removeAllFromSortedUserSet() {
		userZSetOperations.removeRange(USER_SORT_KEY, 0, -1);
	}

	@Override
	public void updateUserRankingList() {
		// user-sort set 에 user hash table 정보 저장 (기존 정보 초기화 후 저장)
		Map<String, RedisUserDTO> userDtoMap = this.getUserInfoMap();
		this.removeAllFromSortedUserSet();
		for (RedisUserDTO redisUserDto : userDtoMap.values()) {
			userZSetOperations.add(USER_SORT_KEY, redisUserDto, redisUserDto.getTier());
		}

		// 랭킹 구하기
		int rank = 0;
		int index = 0;
		int previousTier = Integer.MAX_VALUE;

		Set<RedisUserDTO> userDtoSet = this.getUserInfoSet(0, -1);
		for (RedisUserDTO redisUserDto : userDtoSet) {
			index++;
			Integer tier = redisUserDto.getTier();

			if (tier != previousTier) {
				rank = index;
			}
			previousTier = tier;

			// 기존 정보 삭제 후 추가
			userZSetOperations.remove(USER_SORT_KEY, redisUserDto);
			redisUserDto.setPreviousRank(redisUserDto.getCurrentRank());
			redisUserDto.setCurrentRank(rank);
			userZSetOperations.add(USER_SORT_KEY, redisUserDto, redisUserDto.getTier());
			userHashOperations.put(USER_HASH_KEY, String.valueOf(redisUserDto.getSeq()), redisUserDto);
		}
	}

	@Override
	public RedisUserDTO getMyRankingInfo(Long seq) {
		Set<RedisUserDTO> userDtoSet = this.getUserInfoSet(0, -1);
		for (RedisUserDTO redisUserDto : userDtoSet) {
			if (redisUserDto.getSeq().equals(seq)) {
				return redisUserDto;
			}
		}
		return null;
	}

	@Override
	public void updateTierRankList() {
		int bronze = 0;
		int silver = 0;
		int gold = 0;
		int platinum = 0;
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
			} else if (tier < 400) { // 플래: 300~399
				platinum++;
			} else { // 다이아: 400 이상
				diamond++;
			}
		}

		RedisTierRankDTO redisTierRankDto
			= RedisTierRankDTO.builder()
			.bronze(bronze).silver(silver).gold(gold).platinum(platinum).diamond(diamond)
			.build();

		tierRankHashOperations.put(TIER_RANK_HASH_KEY, "rank", redisTierRankDto);
	}

	@Override
	public RedisTierRankDTO getTierRankList() {
		RedisTierRankDTO redisTierRankDto = tierRankHashOperations.get(TIER_RANK_HASH_KEY, "rank");
		log.info(redisTierRankDto.toString());
		return redisTierRankDto;
	}
}
