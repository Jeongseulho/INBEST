package com.jrjr.inbest.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jrjr.inbest.user.entity.Friend;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

	Boolean existsByFollowingSeqAndFollowedSeq(Long followingSeq, Long followedSeq);

	Optional<Friend> findByFollowingSeqAndFollowedSeq(Long followingSeq, Long followedSeq);

	void deleteByFollowingSeqAndFollowedSeq(Long followingSeq, Long followedSeq);

	Optional<List<Friend>> findAllByFollowedSeq(Long followedSeq);

	Optional<List<Friend>> findAllByFollowingSeq(Long followingSeq);

	Optional<Integer> countByFollowingSeq(Long userSeq);

	Optional<Integer> countByFollowedSeq(Long userSeq);
}
