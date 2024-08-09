package com.security.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.security.Model.ItemPedido;
import com.security.Model.Pedido;
import com.security.Model.Produto;
import com.security.Repositories.PedidoRepository;
import com.security.Repositories.ProdutoRepository;

class PedidoServiceTest {

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private Pedido pedidoMock;

    @Mock
    private Produto produtoMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAdicionarProdutoAoPedido() {
        Long pedidoId = 1L;
        Long produtoId = 1L;
        int quantidade = 2;

        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(pedidoMock));
        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produtoMock));

        List<ItemPedido> itens = new ArrayList<>();
        when(pedidoMock.getItens()).thenReturn(itens);

        pedidoService.adicionarProdutoAoPedido(pedidoId, produtoId, quantidade);

        assertEquals(1, itens.size(), "O item deveria ter sido adicionado ao pedido");
        verify(pedidoRepository).save(pedidoMock);
    }

    @Test
    void testRemoverProdutoDoPedido() {
        Long pedidoId = 1L;
        Long produtoId = 1L;

        when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(pedidoMock));
        
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setProduto(produtoMock);
        when(produtoMock.getId()).thenReturn(produtoId);

        List<ItemPedido> itens = new ArrayList<>(Arrays.asList(itemPedido));
        when(pedidoMock.getItens()).thenReturn(itens);

        pedidoService.removerProdutoDoPedido(pedidoId, produtoId);

        assertEquals(0, itens.size(), "O item deveria ter sido removido do pedido");
        verify(pedidoRepository).save(pedidoMock);
    }
}
