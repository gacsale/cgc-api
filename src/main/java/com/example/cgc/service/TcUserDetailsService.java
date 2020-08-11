package com.example.cgc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.cgc.model.TcRole;
import com.example.cgc.model.TcUser;
import com.example.cgc.repository.TcUserRepository;
import com.example.cgc.repository.TcUserRoleRepository;
import com.example.cgc.jwt.UserPrinciple;

@Service
public class TcUserDetailsService implements UserDetailsService {
	
	@Autowired
    TcUserRepository tcUserRepository;
    
    @Autowired
    TcUserRoleRepository tcUserRoleRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TcUser tcUser = tcUserRepository.findByUsername(username).orElseThrow(
            () -> new UsernameNotFoundException("Usuario no existe: " + username)
        );
        List<TcRole> roles = tcUserRoleRepository.findAllRole(tcUser);
        return UserPrinciple.build(tcUser, roles);
    }

}
