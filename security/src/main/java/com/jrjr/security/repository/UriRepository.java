package com.jrjr.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jrjr.security.entity.Uri;

public interface UriRepository extends JpaRepository<Uri, Long> {
}
