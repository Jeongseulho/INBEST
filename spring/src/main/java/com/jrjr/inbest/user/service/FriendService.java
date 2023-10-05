package com.jrjr.inbest.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jrjr.inbest.user.dto.FriendDTO;
import com.jrjr.inbest.user.entity.Friend;
import com.jrjr.inbest.user.repository.FriendRepository;
import com.jrjr.inbest.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendService {

	private final FriendRepository friendRepository;
	private final UserRepository userRepository;

	/*
		팔로우 하기
	 */
	@Transactional
	public void insertFriend(Long followingSeq, Long followedSeq) throws Exception {
		log.info("팔로잉 대상 seq: {}", followingSeq);
		log.info("팔로워 seq: {}", followedSeq);

		if (followingSeq.equals(followedSeq)) {
			log.info("자기 자신을 팔로우 할 수 없습니다.");
			throw new Exception("자기 자신을 팔로우 할 수 없습니다.");
		}

		this.existsUser(followingSeq);
		this.existsUser(followedSeq);

		// 중복 팔로잉 확인
		if (friendRepository.existsByFollowingSeqAndFollowedSeq(followingSeq, followedSeq)) {
			log.info("이미 {}를 팔로잉 중입니다.", followingSeq);
			throw new Exception("이미 " + followingSeq + "를 팔로잉 중입니다.");
		}

		// 팔로잉 대상이 나를 팔로우하고 있는지 확인
		Optional<Friend> friendEntity =
			friendRepository.findByFollowingSeqAndFollowedSeq(followedSeq, followingSeq);

		boolean isFollowBack = false;

		if (friendEntity.isEmpty()) {
			log.info("팔로잉 대상이 나를 팔로우 하지 않는 중");
		}

		if (friendEntity.isPresent()) {
			log.info("팔로잉 대상이 나를 팔로우 하는 중");
			isFollowBack = true;
			friendEntity.get().updateFollowBack(true);
			friendRepository.save(friendEntity.get());
			log.info("맞팔로우 여부 변경 완료");
		}

		friendRepository.save(
			Friend.builder()
				.followingSeq(followingSeq)
				.followedSeq(followedSeq)
				.isFollowBack(isFollowBack)
				.build());
	}

	/*
		팔로우 취소
	 */
	@Transactional
	public void deleteFriend(Long followingSeq, Long followedSeq) throws Exception {
		log.info("팔로잉 대상 seq: {}", followingSeq);
		log.info("팔로워 seq: {}", followedSeq);
		this.existsUser(followingSeq);
		this.existsUser(followedSeq);

		if (followingSeq.equals(followedSeq)) {
			log.info("자기 자신을 팔로우 취소 할 수 없습니다.");
			throw new Exception("자기 자신을 팔로우 취소 할 수 없습니다.");
		}

		// 팔로잉 여부 확인
		if (!friendRepository.existsByFollowingSeqAndFollowedSeq(followingSeq, followedSeq)) {
			log.info("{}를 팔로우 하고 있지 않습니다.", followingSeq);
			throw new Exception(followingSeq + "를 팔로우 하고 있지 않습니다.");
		}

		// 팔로우 취소
		friendRepository.deleteByFollowingSeqAndFollowedSeq(followingSeq, followedSeq);

		// 맞팔로우 중이라면, 맞팔로우 여부 변경
		Optional<Friend> friendEntity =
			friendRepository.findByFollowingSeqAndFollowedSeq(followedSeq, followingSeq);

		if (friendEntity.isPresent()) {
			friendEntity.get().updateFollowBack(false);
			friendRepository.save(friendEntity.get());
			log.info("맞팔로우 여부 변경 완료");
		}
	}

	/*
		팔로잉 목록 조회
	 */
	public List<FriendDTO> findAllFollowings(Long followedSeq) throws Exception {
		log.info("팔로잉 목록을 조회 할 회원 seq: {}", followedSeq);
		this.existsUser(followedSeq);

		// 팔로잉 중인 회원 정보 조회 (pk 값, 이메일, 닉네임, 프로필 이미지 url, 티어, 맞팔 여부)
		Optional<List<FriendDTO>> followingList = friendRepository.getFollowingList(followedSeq);

		// 팔로잉 중인 회원이 없다면
		return followingList.orElse(null);
	}

	/*
		팔로워 목록 조회
	 */
	public List<FriendDTO> findAllFollowers(Long followingSeq) throws Exception {
		log.info("팔로워 목록을 조회 할 회원 seq: {}", followingSeq);
		this.existsUser(followingSeq);

		// 팔로워 정보 조회 (pk 값, 이메일, 닉네임, 프로필 이미지 url, 티어, 맞팔 여부)
		Optional<List<FriendDTO>> followerList = friendRepository.getFollowerList(followingSeq);

		// 팔로워가 없다면
		return followerList.orElse(null);
	}

	/*
		다른 회원의 팔로잉 목록 조회
	 */
	public List<FriendDTO> findAllOtherFollowings(Long userSeq, Long loginSeq) throws Exception {
		log.info("조회할 회원 seq: {}", userSeq);
		log.info("로그인 회원 seq: {}", loginSeq);
		this.existsUser(userSeq);
		this.existsUser(loginSeq);

		Optional<List<Long>> otherFollowingSeqList = friendRepository.getOtherFollowingSeqList(userSeq);
		if (otherFollowingSeqList.isEmpty()) {
			log.info("{}가 팔로우 하고 있는 회원이 없음", userSeq);
			return null;
		}

		List<FriendDTO> followingList = new ArrayList<>();
		for (Long seq : otherFollowingSeqList.get()) {
			// 본인이라면 continue;
			if (seq.equals(loginSeq)) {
				continue;
			}

			Optional<FriendDTO> friendDto = userRepository.getFriendInfo(seq);
			if (friendDto.isEmpty()) {
				continue;
			}

			friendDto.get().setIsFollowed(friendRepository.existsByFollowingSeqAndFollowedSeq(seq, loginSeq));
			followingList.add(friendDto.get());
		}

		return followingList;
	}

	/*
		다른 회원의 팔로워 목록 조회
	 */
	public List<FriendDTO> findAllOtherFollowers(Long userSeq, Long loginSeq) throws Exception {
		log.info("조회할 회원 seq: {}", userSeq);
		log.info("로그인 회원 seq: {}", loginSeq);
		this.existsUser(userSeq);
		this.existsUser(loginSeq);

		Optional<List<Long>> otherFollowerSeqList = friendRepository.getOtherFollowerSeqList(userSeq);
		if (otherFollowerSeqList.isEmpty()) {
			log.info("{}를 팔로우 하고 있는 회원이 없음", userSeq);
			return null;
		}

		List<FriendDTO> followerList = new ArrayList<>();
		for (Long seq : otherFollowerSeqList.get()) {
			// 본인이라면 continue;
			if (seq.equals(loginSeq)) {
				continue;
			}

			Optional<FriendDTO> friendDto = userRepository.getFriendInfo(seq);
			if (friendDto.isEmpty()) {
				continue;
			}

			friendDto.get().setIsFollowed(friendRepository.existsByFollowingSeqAndFollowedSeq(seq, loginSeq));
			followerList.add(friendDto.get());
		}

		return followerList;
	}

	/*
		회원 존재 여부 확인
	 */
	private void existsUser(Long userSeq) throws Exception {
		if (!userRepository.existsById(userSeq)) {
			log.info(userSeq + " 유저가 없습니다.");
			throw new Exception(userSeq + " 유저가 없습니다.");
		}
	}
}
