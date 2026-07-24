package com.fintech.gestao_financeira.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "pagamentos_despesas_fixas")
public class PagamentoDespesaFixa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "despesa_fixa_id")
    private DespesaFixa despesaFixa;

    @Column(nullable = false)
    private Integer mes;

    @Column(nullable = false)
    private Integer ano;

    @Column(nullable = false)
    private LocalDate dataPagamento;

    private boolean pago = false;
}