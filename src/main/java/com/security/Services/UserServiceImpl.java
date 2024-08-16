package com.security.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.Model.User;
import com.security.Repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User create(User user) {
        // Codificar senha antes de salvar
        user.setSenha(passwordEncoder.encode(user.getSenha()));
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        // Implementação para buscar um usuário pelo e-mail
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean authenticate(String email, String senha) {
        // Implementação para autenticar um usuário
        User user = userRepository.findByEmail(email);
        return user != null && passwordEncoder.matches(senha, user.getSenha());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Implementação para carregar um UserDetails a partir do e-mail (ou username)
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + username);
        }

        // Retornar um UserDetails que representa o usuário encontrado
        return org.springframework.security.core.userdetails.User
            .withUsername(user.getEmail())
            .password(user.getSenha())
            .roles("USER") // Definir as roles do usuário conforme necessário
            .build();
    }
}
