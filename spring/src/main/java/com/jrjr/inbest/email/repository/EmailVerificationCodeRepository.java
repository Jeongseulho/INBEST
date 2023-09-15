package com.jrjr.inbest.email.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jrjr.inbest.email.entity.EmailVerificationCode;

@Repository
public interface EmailVerificationCodeRepository extends CrudRepository<EmailVerificationCode, String> {

}
