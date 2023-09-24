package com.jrjr.invest.rank.repository;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import com.jrjr.invest.rank.dto.RedisUserDTO;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class UserRedisRepository implements UserRepository {

	private final HashOperations<String, String, RedisUserDTO> userHashOperations;
	private final ZSetOperations<String, RedisUserDTO> userZSetOperations;

	static final String USER_HASH_KEY = "user";
	static final String USER_SORT_KEY = "user-sort";

	@Autowired
	public UserRedisRepository(RedisTemplate<String, RedisUserDTO> redisTemplate) {
		this.userHashOperations = redisTemplate.opsForHash();
		this.userZSetOperations = redisTemplate.opsForZSet();
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
	public Set<RedisUserDTO> getSortedUserSet(long start, long end) {
		return userZSetOperations.reverseRange(USER_SORT_KEY, start, end);
	}

	@Override
	public void insertUserInfo(RedisUserDTO redisUserDTO) {
		userHashOperations.put(USER_HASH_KEY, String.valueOf(redisUserDTO.getSeq()), redisUserDTO);
	}

	@Override
	public void updateUserProfileInfo(RedisUserDTO inputRedisUserDTO) {
		RedisUserDTO redisUserDTO = userHashOperations.get(USER_HASH_KEY, String.valueOf(inputRedisUserDTO.getSeq()));
		log.info("변경 전 회원 정보: {}", redisUserDTO.toString());

		redisUserDTO.setNickname(inputRedisUserDTO.getNickname());
		redisUserDTO.setProfileImgSearchName(inputRedisUserDTO.getProfileImgSearchName());

		userHashOperations.put(USER_HASH_KEY, String.valueOf(redisUserDTO.getSeq()), redisUserDTO);
		log.info("변경 후 회원 정보: {}", redisUserDTO);
	}

	@Override
	public void updateUserTierAndRateInfo(Long seq, Integer tier, Integer rate) {
		RedisUserDTO redisUserDTO = this.getUserInfo(seq);
		log.info("변경 전 회원 정보: {}", redisUserDTO.toString());

		redisUserDTO.setTier(tier);
		redisUserDTO.setRate(rate);

		userHashOperations.put(USER_HASH_KEY, String.valueOf(seq), redisUserDTO);
		log.info("변경 후 회원 정보: {}", redisUserDTO);
	}

	@Override
	public void removeAllFromSortedUserSet() {
		userZSetOperations.removeRange(USER_SORT_KEY, 0, -1);
	}

	@Override
	public void sortUserRankingInfo() {
		Map<String, RedisUserDTO> userDTOMap = this.getUserInfoMap();
		for (RedisUserDTO redisUserDTO : userDTOMap.values()) {
			userZSetOperations.add(USER_SORT_KEY, redisUserDTO,
				redisUserDTO.getTier() == null ? 0 : redisUserDTO.getTier());
		}
	}
}
