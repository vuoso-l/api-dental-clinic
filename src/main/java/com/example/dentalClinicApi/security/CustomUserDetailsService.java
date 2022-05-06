package com.example.dentalClinicApi.security;

import com.example.dentalClinicApi.entity.auth.Role;
import com.example.dentalClinicApi.entity.auth.User;
import com.example.dentalClinicApi.repository.auth.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    IUserRepository iUserRepository;

    @Override
    public UserDetails loadUserByUsername(String userNameOrEmail) throws UsernameNotFoundException {
        User user = iUserRepository.findByUserNameOrEmail(userNameOrEmail, userNameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        Collection<GrantedAuthority> authorities = new HashSet<>();
        GrantedAuthority authority;
        for (Role role : user.getRoleUser()) {
            authority = new SimpleGrantedAuthority(role.getName());
            authorities.add(authority);
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }


}
