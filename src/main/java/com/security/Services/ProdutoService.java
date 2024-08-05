package com.security.Services;



import java.util.List;
import java.util.Optional;

import com.security.Model.Produto;

public interface ProdutoService {
   

    Optional<Produto> getProdutoById(Long id);

    Produto createProduto(Produto produto);

    Produto updateProduto( Long id, Produto produto);

    void deleteProduto(Long id);
    
    List<Produto> listAll();

	Produto updateProduto(Produto produto);
}
