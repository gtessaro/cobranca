package com.example.cobranca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.example.cobranca.model.Titulo;

public interface Titulos extends JpaRepository<Titulo,Long>{

    public List<Titulo> findByDescricaoContaining(String descricao);

}
