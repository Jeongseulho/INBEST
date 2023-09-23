package com.jrjr.inbest.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jrjr.inbest.board.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	UserEntity findBySeq(Long seq);
	UserEntity findByEmail(String email);
}

