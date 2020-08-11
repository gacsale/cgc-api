package com.example.cgc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cgc.model.TcDetailSale;
import com.example.cgc.model.TcSale;

@Repository
public interface TcDetailSaleRepository extends JpaRepository<TcDetailSale, Long> {
	
	List<TcDetailSale> findAllByTcSale(TcSale tcSale);

}
