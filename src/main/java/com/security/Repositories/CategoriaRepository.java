package com.security.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.Model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {}
