package com.jrjr.inbest.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jrjr.inbest.user.entity.Friend;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

	Optional<List<Friend>> findAllByFollowedSeq(Long followedSeq);

	Optional<List<Friend>> findAllByFollowingSeq(Long followingSeq);

	Optional<Friend> findByFollowingSeqAndFollowedSeq(Long followingSeq, Long followedSeq);

	@Query("SELECT COUNT(f.followingSeq) FROM Friend f WHERE f.followingSeq = :userSeq")
	Optional<Integer> getFollowingNum(@Param("userSeq") Long userSeq);

	@Query("SELECT COUNT(f.followedSeq) FROM Friend f WHERE f.followedSeq = :userSeq")
	Optional<Integer> getFollowerNum(@Param("userSeq") Long userSeq);
}
