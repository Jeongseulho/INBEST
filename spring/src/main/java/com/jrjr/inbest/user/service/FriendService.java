package com.jrjr.inbest.user.service;

import java.util.ArrayList;
import java.util.List;

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

	@Transactional
	public void insertFriend(Long followingSeq, Long followedSeq) throws Exception {

		User followingUser = userRepository.findById(followingSeq).orElse(null);

		// 팔로잉 유저 예외
		if (followingUser == null) {
			log.info(followingSeq + " 유저가 없습니다.");
			throw new Exception(followingSeq + " 유저가 없습니다.");
		}

		User followedUser = userRepository.findById(followedSeq).orElse(null);

		//팔로워 유저 예외
		if (followedUser == null) {
			log.info(followedSeq + " 유저가 없습니다.");
			throw new Exception(followedSeq + " 유저가 없습니다.");
		}

		Friend friend = Friend.builder()
			.followingSeq(followingSeq)
			.followedSeq(followedSeq)
			.build();

		friendRepository.save(friend);
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

	@Transactional
	public void deleteFollowing(Long followingSeq, Long followedSeq) throws Exception {
		User followingUser = userRepository.findById(followingSeq).orElse(null);

		//팔로잉 유저 예외
		if (followingUser == null) {
			log.info(followingSeq + " 유저가 없습니다.");
			throw new Exception(followingSeq + " 유저가 없습니다.");
		}

		User followedUser = userRepository.findById(followedSeq).orElse(null);

		//팔로워 유저 예외
		if (followedUser == null) {
			log.info(followedSeq + " 유저가 없습니다.");
			throw new Exception(followedSeq + " 유저가 없습니다.");
		}

		Friend friend = friendRepository.findByFollowingSeqAndFollowedSeq(followingSeq, followedSeq).orElse(null);

		//친구가 아닌경우
		if (friend == null) {
			throw new Exception(followedSeq + "유저는 " + followingSeq + " 유저를 팔로잉 하고 있지 않습니다.");
		}

		friendRepository.delete(friend);
	}
}
