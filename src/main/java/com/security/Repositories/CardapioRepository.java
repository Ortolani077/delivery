package com.security.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.security.Model.Cardapio;

@Repository
public interface CardapioRepository extends JpaRepository<Cardapio, Long> {
}
