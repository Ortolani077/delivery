package com.security.Controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.security.Model.Pedido;
import com.security.Model.PedidoFormDTO;
import com.security.Model.User;
import com.security.Repositories.PedidoRepository;
import com.security.Repositories.UserRepository;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/criar")
    public ResponseEntity<Pedido> criarPedido(@RequestBody PedidoFormDTO pedidoFormDTO) {
        // Verifica se o cliente já existe pelo email
        User cliente = userRepository.findByEmail(pedidoFormDTO.getEmailCliente());
        if (cliente == null) {
            // Se o cliente não existir, cria um novo
            cliente = new User();
            cliente.setNome(pedidoFormDTO.getNomeCliente());
            cliente.setEmail(pedidoFormDTO.getEmailCliente());
            cliente.setTelefone(pedidoFormDTO.getTelefoneCliente());
            // Defina a senha aqui se necessário, caso o modelo User exija
            cliente = userRepository.save(cliente);
        }

        // Cria o pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setEnderecoCliente(pedidoFormDTO.getEnderecoCliente());
        pedido.setEntrega(pedidoFormDTO.isEntrega());
        pedido.setObservacoes(pedidoFormDTO.getObservacoes());
        pedido.setPreco(pedidoFormDTO.getPreco());
        pedido.setTelefoneCliente(pedidoFormDTO.getTelefoneCliente());
        pedido.setDataPedido(new Date());

        // Salva o pedido no banco de dados
        Pedido savedPedido = pedidoRepository.save(pedido);

        // Retorna o pedido criado com sucesso
        return ResponseEntity.ok(savedPedido);
    }
}
