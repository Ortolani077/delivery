package com.security.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.Model.Endereco;
import com.security.Model.User;
import com.security.Model.UserDTO;
import com.security.Repositories.EnderecoRepository;
import com.security.Repositories.UserRepository;
import com.security.Services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @PostMapping
    public ResponseEntity<User> criarUser(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setNome(userDTO.getNome());
        user.setEmail(userDTO.getEmail());
        user.setTelefone(userDTO.getTelefone());
        user.setSenha(userDTO.getSenha());

        // Verifica se o DTO contém IDs de endereços
        if (userDTO.getEnderecoIds() != null && !userDTO.getEnderecoIds().isEmpty()) {
            for (Long enderecoId : userDTO.getEnderecoIds()) {
                Optional<Endereco> enderecoOptional = enderecoRepository.findById(enderecoId);
                enderecoOptional.ifPresent(user::addEndereco); // Adiciona o endereço ao user
            }
        }

        // Usa o serviço para criar o user
        User userSalvo = userService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userSalvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(user -> ResponseEntity.ok().body(user))
                              .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok().body(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> atualizarUser(@PathVariable("id") Long id, @RequestBody UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setNome(userDTO.getNome());
            user.setEmail(userDTO.getEmail());
            user.setTelefone(userDTO.getTelefone());
            user.setSenha(userDTO.getSenha());

            // Remove todos os endereços associados ao user
            user.getEnderecos().clear();

            // Adiciona os novos endereços fornecidos no DTO
            if (userDTO.getEnderecoIds() != null && !userDTO.getEnderecoIds().isEmpty()) {
                for (Long enderecoId : userDTO.getEnderecoIds()) {
                    Optional<Endereco> enderecoOptional = enderecoRepository.findById(enderecoId);
                    enderecoOptional.ifPresent(user::addEndereco);
                }
            }

            // Salva as atualizações do user no banco de dados
            User userAtualizado = userRepository.save(user);
            return ResponseEntity.ok().body(userAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirUser(@PathVariable("id") Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
