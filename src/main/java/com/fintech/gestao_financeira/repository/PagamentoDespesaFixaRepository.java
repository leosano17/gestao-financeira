package com.fintech.gestao_financeira.repository;

import com.fintech.gestao_financeira.model.DespesaFixa;
import com.fintech.gestao_financeira.model.PagamentoDespesaFixa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PagamentoDespesaFixaRepository extends JpaRepository<PagamentoDespesaFixa, Long> {
    Optional<PagamentoDespesaFixa> findByDespesaFixaAndMesAndAno(DespesaFixa despesaFixa, Integer mes, Integer ano);
    List<PagamentoDespesaFixa> findByDespesaFixaInAndMesAndAno(List<DespesaFixa> despesas, Integer mes, Integer ano);
}