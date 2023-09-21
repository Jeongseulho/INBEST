package com.jrjr.inbest.board.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jrjr.inbest.board.entity.BoardEntity;
import com.jrjr.inbest.board.entity.BoardImgEntity;

@Repository
public interface BoardImgRepository extends MongoRepository<BoardImgEntity,String> {
}
