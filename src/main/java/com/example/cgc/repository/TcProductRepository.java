package com.example.cgc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cgc.model.TcProduct;

@Repository
public interface TcProductRepository extends JpaRepository<TcProduct, Long> {
	
	Optional<TcProduct> findBySku(Long sku);

}
