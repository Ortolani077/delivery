package com.security.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.Model.Endereco;
import com.security.Model.EnderecoDTO;
import com.security.Model.User;
import com.security.Model.UserDTO;
import com.security.Repositories.EnderecoRepository;
import com.security.Repositories.UserRepository;

@RestController
@RequestMapping("/delivery/usuarios")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @PostMapping("/criar")
    @Transactional
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
        if (userDTO == null) {
            return ResponseEntity.badRequest().body("Dados do usuário não fornecidos.");
        }

        User user = new User();
        user.setNome(userDTO.getNome());
        user.setEmail(userDTO.getEmail());
        user.setTelefone(userDTO.getTelefone());

        // Salvar endereços
        if (userDTO.getEnderecos() != null) {
            userDTO.getEnderecos().forEach(enderecoDTO -> {
                Endereco endereco = new Endereco();
                endereco.setRua(enderecoDTO.getRua());
                endereco.setBairro(enderecoDTO.getBairro());
                endereco.setCidade(enderecoDTO.getCidade());
                endereco.setEstado(enderecoDTO.getEstado());
                endereco.setCep(enderecoDTO.getCep());

                enderecoRepository.save(endereco);
                user.addEndereco(endereco);
            });
        }

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso.");
    }
}
