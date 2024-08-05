package com.security.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.Model.User;
import com.security.Repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository clienteRepository;

    @Override
    public User create(User user) {
        // Implementação para criar um cliente
        return clienteRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        // Implementação para buscar um cliente pelo e-mail
        return clienteRepository.findByEmail(email);
    }

    @Override
    public boolean authenticate(String email, String senha) {
        // Implementação para autenticar um cliente
    	User user = clienteRepository.findByEmail(email);
        return user != null && user.getSenha().equals(senha);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Implementação para carregar um UserDetails a partir do e-mail (ou username)
    	User user = clienteRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Cliente não encontrado com o e-mail: " + username);
        }

        // Retornar um UserDetails que representa o cliente encontrado
        return org.springframework.security.core.userdetails.User
            .withUsername(user.getEmail())
            .password(user.getSenha())
            .roles("USER") // Definir as roles do usuário conforme necessário
            .build();
    }
}
