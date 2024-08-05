package com.security.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.security.Model.Produto;
import com.security.Repositories.ProdutoRepository;

@Service
public class ProdutoServicesImplementation implements ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoServicesImplementation(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public Optional<Produto> getProdutoById(Long id) {
        return produtoRepository.findById(id);
    }

    @Override
    public Produto createProduto(Produto produto) {
        if (produto.getId() != null) {
            throw new RuntimeException("O produto já está cadastrado");
        }
        return produtoRepository.save(produto);
    }

    @Override
    public Produto updateProduto(Long id, Produto produto) {
        if (produto.getId() == null) {
            throw new RuntimeException("ID do produto está nulo, informe um ID válido");
        }
        return produtoRepository.save(produto);
    }

    @Override
    public void deleteProduto(Long id) {
        produtoRepository.deleteById(id);
    }

    @Override
    public List<Produto> listAll() {
        return produtoRepository.findAll();
    }

    @Override
    public Produto updateProduto(Produto produto) {
        if (produto.getId() == null) {
            throw new RuntimeException("ID do produto está nulo, informe um ID válido");
        }
        return produtoRepository.save(produto);
    }
}
