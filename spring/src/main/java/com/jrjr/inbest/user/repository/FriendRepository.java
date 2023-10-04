package com.jrjr.inbest.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jrjr.inbest.user.dto.FriendDTO;
import com.jrjr.inbest.user.entity.Friend;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

	Boolean existsByFollowingSeqAndFollowedSeq(Long followingSeq, Long followedSeq);

	Optional<Friend> findByFollowingSeqAndFollowedSeq(Long followingSeq, Long followedSeq);

	void deleteByFollowingSeqAndFollowedSeq(Long followingSeq, Long followedSeq);

	@Query(
		"SELECT NEW com.jrjr.inbest.user.dto.FriendDTO(u.seq, u.email, u.nickname, u.profileImgSearchName, SUM(t.tier), f.isFollowBack) "
			+ "FROM Friend f "
			+ "JOIN User u ON f.followingSeq = u.seq "
			+ "LEFT JOIN Tier t ON f.followingSeq = t.userSeq "
			+ "WHERE f.followedSeq = :userSeq "
			+ "GROUP BY f.followingSeq, f.isFollowBack "
			+ "ORDER BY f.isFollowBack DESC")
	Optional<List<FriendDTO>> getFollowingList(Long userSeq);

	@Query(
		"SELECT NEW com.jrjr.inbest.user.dto.FriendDTO(u.seq, u.email, u.nickname, u.profileImgSearchName, SUM(t.tier), f.isFollowBack) "
			+ "FROM Friend f "
			+ "JOIN User u ON f.followedSeq = u.seq "
			+ "LEFT JOIN Tier t ON f.followedSeq = t.userSeq "
			+ "WHERE f.followingSeq = :userSeq "
			+ "GROUP BY f.followedSeq, f.isFollowBack "
			+ "ORDER BY f.isFollowBack DESC")
	Optional<List<FriendDTO>> getFollowerList(Long userSeq);

	Optional<Integer> countByFollowingSeq(Long userSeq);

	Optional<Integer> countByFollowedSeq(Long userSeq);

	void deleteByFollowingSeq(Long userSeq);

	void deleteByFollowedSeq(Long userSeq);
}
