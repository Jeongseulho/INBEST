package com.jrjr.inbest.dictionary.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jrjr.inbest.dictionary.entity.FinancialWordEntity;

@Repository
public interface FinancialWordRepository extends MongoRepository<FinancialWordEntity, String> {
	Page<FinancialWordEntity> findAll(Pageable pageable);
	Page<FinancialWordEntity> findAllByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword,
		Pageable pageable);
}
