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

    Page<Transacao> findByUsuarioAndDataBetween(Usuario usuario, LocalDate dataInicio, LocalDate dataFim, Pageable pageable);

    Page<Transacao> findByUsuarioAndDataBetweenAndTipo(Usuario usuario, LocalDate dataInicio, LocalDate dataFim, TipoTransacao tipo, Pageable pageable);

    @Query("SELECT t FROM Transacao t WHERE t.usuario = :usuario " +
            "AND t.data >= :dataInicio AND t.data <= :dataFim " +
            "AND (:categoriaId IS NULL OR t.categoria.id = :categoriaId)")
    Page<Transacao> filtrarPorPeriodo(
            @Param("usuario") Usuario usuario,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim,
            @Param("categoriaId") Long categoriaId,
            Pageable pageable);
}