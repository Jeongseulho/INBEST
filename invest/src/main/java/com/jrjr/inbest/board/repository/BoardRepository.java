package com.jrjr.inbest.board.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jrjr.inbest.board.entity.BoardEntity;

@Repository
public interface BoardRepository extends MongoRepository<BoardEntity, String> {
	Page<BoardEntity> findAll(Pageable pageable);

	Page<BoardEntity> findAllByTitleContainingOrContextContaining(String titleKeyword, String contextKeyword,
		Pageable pageable);

	List<BoardEntity> findByCreatedDateBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

	Long countByTitleContainingOrContextContaining(String titleKeyword, String contextKeyword);
}
