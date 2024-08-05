package com.security.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.security.Model.Pedido;
import com.security.Repositories.PedidoRepository;

class PedidoServiceTest {

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private Pedido pedidoMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCriarPedido() {
        // Configura o mock para retornar o pedido com status PENDENTE e data definida
        when(pedidoRepository.save(pedidoMock)).thenAnswer(invocation -> {
            Pedido pedido = invocation.getArgument(0);
            pedido.setDataPedido(new Date()); // Define a data atual como a data do pedido
            pedido.setStatus("PENDENTE"); // Define o status como "PENDENTE"
            return pedido;
        });

        // Cria um pedido usando o serviço
        Pedido createdPedido = pedidoService.criarPedido(pedidoMock);

        // Verifica se o pedido retornado não é nulo e se o status é o esperado
        assertNotNull(createdPedido, "O pedido criado não deve ser nulo");
        assertEquals("PENDENTE", createdPedido.getStatus(), "O status do pedido deve ser PENDENTE");
        assertNotNull(createdPedido.getDataPedido(), "A data do pedido não deve ser nula");
    }

    @Test
    void testBuscarPedidoPorId() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedidoMock));

        Pedido foundPedido = pedidoService.buscarPedidoPorId(1L);

        assertNotNull(foundPedido, "O pedido encontrado não deve ser nulo");
        assertEquals(pedidoMock, foundPedido, "O pedido encontrado deve ser igual ao pedido mockado");
    }

    @Test
    void testBuscarPedidoPorIdNaoEncontrado() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.empty());

        Pedido foundPedido = pedidoService.buscarPedidoPorId(1L);

        assertNull(foundPedido, "O pedido não encontrado deve ser nulo");
    }

    @Test
    void testListarPedidos() {
        List<Pedido> pedidos = Arrays.asList(pedidoMock);
        when(pedidoRepository.findAll()).thenReturn(pedidos);

        List<Pedido> foundPedidos = pedidoService.listarPedidos();

        assertNotNull(foundPedidos, "A lista de pedidos não deve ser nula");
        assertEquals(1, foundPedidos.size(), "A lista de pedidos deve conter um pedido");
        assertEquals(pedidoMock, foundPedidos.get(0), "O pedido retornado deve ser igual ao pedido mockado");
    }

    @Test
    void testDeletarPedido() {
        pedidoService.deletarPedido(1L);

        verify(pedidoRepository).deleteById(1L);
    }
}
