package com.security.Services;

import java.util.List;
import java.util.Optional;
import com.security.Model.Produto;



import java.util.List;
import java.util.Optional;
import com.security.Model.Produto;

public interface ProdutoService {
    Optional<Produto> getProdutoById(Long id);
    Produto createProduto(Produto produto);
    Produto updateProduto(Long id, Produto produto);
    void deleteProduto(Long id);
    List<Produto> listAll();
    
    // Método para buscar produtos por ID da categoria (usando Long)
    List<Produto> getProdutosByCategoria(Long categoriaId);
    
    // Método para buscar produtos por ID da categoria usando SQL nativo
    List<Object[]> getProdutosByCategoriaNative(Long categoriaId);
}
