package com.security.Controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.security.Model.ItemPedido;
import com.security.Model.ItemPedidoDTO;
import com.security.Model.Pedido;
import com.security.Model.PedidoDTO;
import com.security.Model.Produto;
import com.security.Model.User;
import com.security.Repositories.PedidoRepository;
import com.security.Repositories.ProdutoRepository;
import com.security.Repositories.UserRepository;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/criar")
    public ResponseEntity<String> criarPedido(@RequestBody PedidoDTO pedidoDTO) {
        Optional<User> clienteOptional = userRepository.findById(pedidoDTO.getClienteId());
        if (clienteOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Cliente com ID " + pedidoDTO.getClienteId() + " não encontrado.");
        }

        Pedido pedido = new Pedido();
        pedido.setCliente(clienteOptional.get());
        pedido.setDataPedido(new Date()); // Convertido de String para Date conforme necessário
        pedido.setStatus(pedidoDTO.getStatus());
        pedido.setEntrega(pedidoDTO.isEntrega());

        StringBuilder observacoes = new StringBuilder(pedidoDTO.getObservacoes());
        if (pedidoDTO.getTroco() != null) {
            observacoes.append(" Troco: R$ ").append(pedidoDTO.getTroco().toString());
        }
        pedido.setObservcoes(observacoes.toString());

        // Adicionar itens ao pedido
        List<ItemPedido> itensPedido = new ArrayList<>();
        for (ItemPedidoDTO itemDTO : pedidoDTO.getItens()) {
            Optional<Produto> produtoOptional = produtoRepository.findById(itemDTO.getProdutoId());
            if (produtoOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("Produto com ID " + itemDTO.getProdutoId() + " não encontrado.");
            }

            Produto produto = produtoOptional.get();
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setProduto(produto);
            itemPedido.setQuantidade(itemDTO.getQuantidade());
            itemPedido.setPreco(produto.getPreco());
            itemPedido.setPedido(pedido);

            itensPedido.add(itemPedido);
        }

        pedido.setItens(itensPedido);
        pedidoRepository.save(pedido);

        return ResponseEntity.ok("Pedido criado com sucesso.");
    }
}