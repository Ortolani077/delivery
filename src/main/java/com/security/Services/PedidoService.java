package com.security.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.Model.ItemPedido;
import com.security.Model.Pedido;
import com.security.Model.Produto;
import com.security.Repositories.PedidoRepository;
import com.security.Repositories.ProdutoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    // Adicionar produto ao pedido
    public void adicionarProdutoAoPedido(Long pedidoId, Long produtoId, int quantidade) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setProduto(produto);
        itemPedido.setQuantidade(quantidade);
        itemPedido.setPreco(produto.getPreco());

        pedido.getItens().add(itemPedido);
        pedidoRepository.save(pedido);
    }

    // Remover produto do pedido
    public void removerProdutoDoPedido(Long pedidoId, Long produtoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        
        pedido.getItens().removeIf(item -> item.getProduto().getId().equals(produtoId));
        pedidoRepository.save(pedido);
    }

    // Listar todos os produtos de um pedido
    public List<ItemPedido> listarProdutosDoPedido(Long pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        return pedido.getItens();
    }

    // Atualizar a quantidade de um produto no pedido
    public void atualizarQuantidadeProduto(Long pedidoId, Long produtoId, int novaQuantidade) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        Optional<ItemPedido> itemPedidoOptional = pedido.getItens().stream()
                .filter(item -> item.getProduto().getId().equals(produtoId))
                .findFirst();

        if (itemPedidoOptional.isPresent()) {
            ItemPedido itemPedido = itemPedidoOptional.get();
            itemPedido.setQuantidade(novaQuantidade);
            pedidoRepository.save(pedido);
        } else {
            throw new RuntimeException("Produto não encontrado no pedido");
        }
    }

    // Buscar pedido por ID
    public Pedido buscarPedidoPorId(Long pedidoId) {
        return pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    // Métodos adicionais conforme necessário
}
