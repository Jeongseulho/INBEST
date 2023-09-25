package com.jrjr.invest.group.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jrjr.invest.group.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findByNameContains(String keyword);

	Optional<User> findByEmail(String email);

	Boolean existsByEmail(String email);

	Boolean existsByNickname(String nickname);
}

