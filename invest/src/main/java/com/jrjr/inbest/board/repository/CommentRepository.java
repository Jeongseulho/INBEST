package com.jrjr.inbest.board.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jrjr.inbest.board.entity.BoardEntity;
import com.jrjr.inbest.board.entity.CommentEntity;

@Repository
public interface CommentRepository extends MongoRepository<CommentEntity,String> {

}
