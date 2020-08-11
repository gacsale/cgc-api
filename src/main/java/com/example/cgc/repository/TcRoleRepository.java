package com.example.cgc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cgc.model.TcRole;

@Repository
public interface TcRoleRepository extends JpaRepository<TcRole, Long> {

	Optional<TcRole> findByStatusId(byte statusId);

}
