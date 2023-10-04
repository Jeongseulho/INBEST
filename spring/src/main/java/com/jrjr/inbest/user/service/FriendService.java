package com.jrjr.inbest.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jrjr.inbest.user.dto.UserDto;
import com.jrjr.inbest.user.entity.Friend;
import com.jrjr.inbest.user.entity.User;
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

	public List<UserDto> findAllFollowings(Long followedSeq) {
		User followedUser = userRepository.findById(followedSeq).orElse(null);

		//팔로워 유저 예외
		if (followedUser == null) {
			log.info(followedSeq + " 유저가 없습니다.");
		}

		List<Friend> friendList = friendRepository.findAllByFollowedSeq(followedSeq).orElse(new ArrayList<>());
		//반환할 팔로잉 목록
		List<UserDto> followingList = new ArrayList<>();

		//팔로잉 목록을 dto 로 변경
		for (Friend friend : friendList) {
			User following = userRepository.findById(friend.getFollowingSeq())
				.orElse(null);

			//없는 유저 스킵
			if (following == null) {
				continue;
			}

			//팔로잉 목록에 추가
			followingList.add(following.convertToUserDto(following));
		}

		return followingList;
	}

	public List<UserDto> findAllFollowers(Long followedSeq) {
		User followedUser = userRepository.findById(followedSeq).orElse(null);

		//팔로워 유저 예외
		if (followedUser == null) {
			log.info(followedSeq + " 유저가 없습니다.");
		}

		List<Friend> friendList = friendRepository.findAllByFollowingSeq(followedSeq).orElse(new ArrayList<>());
		// 반환할 팔로잉 목록
		List<UserDto> followerList = new ArrayList<>();

		// 팔로잉 목록을 dto 로 변경
		for (Friend friend : friendList) {
			User follower = userRepository.findById(friend.getFollowedSeq())
				.orElse(null);

			//없는 유저 스킵
			if (follower == null) {
				continue;
			}

			//팔로잉 목록에 추가
			followerList.add(follower.convertToUserDto(follower));
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
