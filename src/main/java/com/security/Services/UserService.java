package com.security.Services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.security.Model.User;

public interface UserService extends UserDetailsService {

	User create(User user);
    // Outros métodos específicos do serviço Cliente, se necessário

	User findByEmail(String email);

	boolean authenticate(String email, String senha);
}
