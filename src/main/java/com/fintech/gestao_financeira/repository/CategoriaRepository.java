package com.fintech.gestao_financeira.repository;

import com.fintech.gestao_financeira.model.Categoria;
import com.fintech.gestao_financeira.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByUsuario(Usuario usuario);
    boolean existsByNomeAndUsuario(String nome, Usuario usuario);
}