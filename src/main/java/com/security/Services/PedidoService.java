package com.security.Services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.Model.Pedido;
import com.security.Repositories.PedidoRepository;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido criarPedido(Pedido pedido) {
        pedido.setDataPedido(new Date()); // Define a data atual como a data do pedido
        pedido.setStatus("PENDENTE"); // Define o status como "PENDENTE" por padrão
        return pedidoRepository.save(pedido);
    }

    public Pedido buscarPedidoPorId(Long id) {
        Optional<Pedido> optionalPedido = pedidoRepository.findById(id);
        return optionalPedido.orElse(null);
    }

    public List<Pedido> listarPedidos() {
        return (List<Pedido>) pedidoRepository.findAll();
    }

    public void deletarPedido(Long id) {
        pedidoRepository.deleteById(id);
    }

    // Métodos adicionais conforme necessário
}
