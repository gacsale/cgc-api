package com.example.cgc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cgc.model.TcClient;
import com.example.cgc.model.TcSale;

@Repository
public interface TcSaleRepository extends JpaRepository<TcSale, Long> {
	
	Optional<TcSale> findByTcClient(TcClient tcClient);

}
