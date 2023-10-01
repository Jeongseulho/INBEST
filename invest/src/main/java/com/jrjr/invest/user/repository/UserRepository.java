package com.jrjr.invest.user.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jrjr.invest.rank.dto.RedisUserDTO;
import com.jrjr.invest.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	Boolean existsByEmail(String email);

	Boolean existsByNickname(String nickname);

	User findByNickname(String nickname);

	List<User> findByNicknameContains(String keyword);

	User findBySeq(Long seq);

	Long countByCreatedDateAfter(LocalDateTime localDateTime);

	@Query("SELECT NEW com.jrjr.invest.rank.dto.RedisUserDTO(u.seq, u.nickname, u.profileImgSearchName) FROM User u")
	List<RedisUserDTO> getUserInfo();
}