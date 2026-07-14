package com.fintech.gestao_financeira.repository;

import com.fintech.gestao_financeira.model.TipoTransacao;
import com.fintech.gestao_financeira.model.Transacao;
import com.fintech.gestao_financeira.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    Page<Transacao> findByUsuario(Usuario usuario, Pageable pageable);

    @Query("SELECT t FROM Transacao t WHERE t.usuario = :usuario " +
            "AND (:dataInicio IS NULL OR t.data >= :dataInicio) " +
            "AND (:dataFim IS NULL OR t.data <= :dataFim) " +
            "AND (:tipo IS NULL OR t.tipo = :tipo) " +
            "AND (:categoriaId IS NULL OR t.categoria.id = :categoriaId)")
    Page<Transacao> filtrar(
            @Param("usuario") Usuario usuario,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim,
            @Param("tipo") TipoTransacao tipo,
            @Param("categoriaId") Long categoriaId,
            Pageable pageable);
}