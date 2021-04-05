package com.websiteSearch.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.websiteSearch.entity.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>{

	 Optional<VerificationToken> findByToken(String token);
}
