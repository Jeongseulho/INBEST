package com.jrjr.invest.rank.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.jrjr.invest.global.exception.NotFoundException;
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
		닉네임으로 개인 랭킹 정보 검색하기
	 */
	@Override
	public List<RedisUserDTO> getUserRankingInfoByNickname(String nickname) {
		// user table 에서 nickname 으로 userSeq 검색
		Long userSeq = userRepository.findSeqByNickname(nickname);
		if (userSeq == null) {
			throw new NotFoundException(nickname + " 닉네임으로 검색된 회원 정보가 없음");
		}
		log.info(nickname + "의 userSeq: " + userSeq);

		// redis user hash table 에서 userSeq 으로 검색 후 랭킹 정보 조회
		RedisUserDTO userDto = userRankRedisRepository.getUserInfo(userSeq);
		if (userDto == null) {
			throw new NotFoundException(nickname + " 닉네임으로 검색된 회원 정보가 없음");
		}
		Integer currentRank = userDto.getCurrentRank();
		log.info(nickname + "의 현재 랭킹: " + currentRank);

		// +- 10 등 범위 산정
		long start = currentRank > 10 ? currentRank - 10 : 1;
		long end = currentRank + 10;
		log.info("조회 랭킹 범위: " + start + " ~ " + end);

		// 해당 범위 랭킹 정보 검색
		List<RedisUserDTO> userRankingInfoList = userRankRedisRepository.getUserRankingInfoList(start, end);
		log.info(userRankingInfoList.toString());

		return userRankingInfoList;
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
