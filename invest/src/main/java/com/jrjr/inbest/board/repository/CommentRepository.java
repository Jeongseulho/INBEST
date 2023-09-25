package com.jrjr.inbest.board.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jrjr.inbest.board.entity.CommentEntity;

@Repository
public interface CommentRepository extends MongoRepository<CommentEntity, String> {
}
