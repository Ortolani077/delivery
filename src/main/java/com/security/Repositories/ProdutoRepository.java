package com.security.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.Model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {}
