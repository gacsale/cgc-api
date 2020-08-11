package com.example.cgc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cgc.model.TcClient;
import com.example.cgc.model.TcUser;

@Repository
public interface TcClientRepository extends JpaRepository<TcClient, Long> {
	
	Optional<TcClient> findByTcUser(TcUser tcUser);

}
