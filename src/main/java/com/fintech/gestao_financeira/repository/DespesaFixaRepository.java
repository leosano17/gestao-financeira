package com.fintech.gestao_financeira.repository;

import com.fintech.gestao_financeira.model.DespesaFixa;
import com.fintech.gestao_financeira.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DespesaFixaRepository extends JpaRepository<DespesaFixa, Long> {
    List<DespesaFixa> findByUsuarioAndAtiva(Usuario usuario, boolean ativa);
}