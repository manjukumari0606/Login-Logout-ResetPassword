package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.PasswordResetToken;

public interface TokenRepository extends JpaRepository<PasswordResetToken, Long>{

	PasswordResetToken findByToken(String token);

}
