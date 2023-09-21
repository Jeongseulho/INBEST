package com.jrjr.inbest.board.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jrjr.inbest.board.entity.CoCommentEntity;
import com.jrjr.inbest.board.entity.CommentEntity;

@Repository
public interface CoCommentRepository extends MongoRepository<CoCommentEntity,String> {

}
