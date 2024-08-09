package com.security.Controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.Model.ItemPedido;
import com.security.Model.Pedido;
import com.security.Model.Produto;
import com.security.Repositories.PedidoRepository;
import com.security.Repositories.ProdutoRepository;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private static final ItemPedido ItemPedido = null;

	@Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @PostMapping("/{pedidoId}/adicionar/{produtoId}")
    public ResponseEntity<?> adicionarProdutoAoPedido(@PathVariable Long pedidoId, @PathVariable Long produtoId) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(pedidoId);
        if (pedidoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Produto> produtoOptional = produtoRepository.findById(produtoId);
        if (produtoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Pedido pedido = pedidoOptional.get();
        Produto produto = produtoOptional.get();

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setProduto(produto);
        itemPedido.setQuantidade(1); // Define a quantidade conforme necessário
        itemPedido.setPreco(produto.getPreco());

        pedido.getItens().add(itemPedido);
        pedidoRepository.save(pedido);

        return ResponseEntity.ok().build();
    }
}
