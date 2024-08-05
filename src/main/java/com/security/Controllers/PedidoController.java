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

import com.security.Model.Pedido;
import com.security.Model.PedidoDTO;
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

    // Create - POST
    @PostMapping
    public ResponseEntity<Object> criarPedido(@RequestBody PedidoDTO pedidoDTO) {
        // Verificar se o user está presente no pedido
        if (pedidoDTO.getClienteId() == null) {
            return ResponseEntity.badRequest().body("{\"message\": \"O user associado ao pedido não foi especificado.\"}");
        }

        // Verificar se o user existe no banco de dados
        Optional<User> userOptional = userRepository.findById(pedidoDTO.getClienteId());
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("{\"message\": \"O user associado ao pedido não existe no banco de dados.\"}");
        }

        User user = userOptional.get();

        // Criar a entidade Pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(user);
        pedido.setDataPedido(pedidoDTO.getDataPedido());
        pedido.setStatus(pedidoDTO.getStatus());

        // Salvar o pedido no banco de dados
        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoSalvo);
    }

    // Read - GET all
    @GetMapping
    public ResponseEntity<List<Pedido>> listarPedidos() {
        List<Pedido> pedidos = (List<Pedido>) pedidoRepository.findAll();
        return ResponseEntity.ok(pedidos);
    }

    // Read - GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPedidoPorId(@PathVariable("id") Long id) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);
        return pedidoOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update - PUT
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizarPedido(@PathVariable Long id, @RequestBody PedidoDTO pedidoDTO) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);
        if (pedidoOptional.isPresent()) {
            Pedido pedido = pedidoOptional.get();
            pedido.setDataPedido(pedidoDTO.getDataPedido());
            pedido.setStatus(pedidoDTO.getStatus());
            Pedido pedidoAtualizado = pedidoRepository.save(pedido);
            return ResponseEntity.ok(pedidoAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete - DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirPedido(@PathVariable Long id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
