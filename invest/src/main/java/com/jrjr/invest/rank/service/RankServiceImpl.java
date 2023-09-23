package com.jrjr.invest.rank.service;

import java.util.Map;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.jrjr.invest.rank.dto.RedisUserDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RankServiceImpl implements RankService {

	private final RedisTemplate<String, RedisUserDTO> redisUserDtoRedisTemplate;

	@Override
	public void insertUserRankingInfo(RedisUserDTO redisUserDTO) {
		log.info("회원 정보: {}", redisUserDTO.toString());

		String hashKey = "user";
		HashOperations<String, String, RedisUserDTO> redisUserDTOHashOperations = redisUserDtoRedisTemplate.opsForHash();
		redisUserDTOHashOperations.put(hashKey, String.valueOf(redisUserDTO.getSeq()), redisUserDTO);

		Map<String, RedisUserDTO> redisUserDTOMap = redisUserDTOHashOperations.entries(hashKey);
		for (String seq : redisUserDTOMap.keySet()) {
			log.info(redisUserDTOMap.get(seq).toString());
		}
	}

	@Override
	public void updateUserRankingInfo(RedisUserDTO inputRedisUserDTO) {
		log.info("변경 요청 회원 정보: {}", inputRedisUserDTO.toString());

		String hashKey = "user";
		HashOperations<String, String, RedisUserDTO> redisUserDTOHashOperations = redisUserDtoRedisTemplate.opsForHash();
		RedisUserDTO redisUserDTO = redisUserDTOHashOperations.get(hashKey, String.valueOf(inputRedisUserDTO.getSeq()));
		log.info("변경 전 회원 정보: {}", redisUserDTO.toString());
		redisUserDTO.setNickname(inputRedisUserDTO.getNickname());
		redisUserDTO.setProfileImgSearchName(inputRedisUserDTO.getProfileImgSearchName());
		redisUserDTOHashOperations.put(hashKey, String.valueOf(redisUserDTO.getSeq()), redisUserDTO);
		log.info("변경 후 회원 정보: {}", redisUserDTO);

		Map<String, RedisUserDTO> redisUserDTOMap = redisUserDTOHashOperations.entries(hashKey);
		for (String seq : redisUserDTOMap.keySet()) {
			log.info(redisUserDTOMap.get(seq).toString());
		}
	}
}
