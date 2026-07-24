package com.fintech.gestao_financeira.controller;

import com.fintech.gestao_financeira.model.PagamentoDespesaFixa;
import com.fintech.gestao_financeira.service.PagamentoDespesaFixaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/pagamentos-despesas")
@RequiredArgsConstructor
public class PagamentoDespesaFixaController {

    private final PagamentoDespesaFixaService pagamentoService;

    @GetMapping("/mes/{mes}/ano/{ano}")
    public List<PagamentoDespesaFixa> listarDoMes(
            @PathVariable Integer mes,
            @PathVariable Integer ano,
            Authentication auth) {
        return pagamentoService.listarDoMes(auth.getName(), mes, ano);
    }

    @PostMapping("/pagar/{despesaFixaId}")
    public ResponseEntity<PagamentoDespesaFixa> pagar(
            @PathVariable Long despesaFixaId,
            Authentication auth) {
        return ResponseEntity.ok(pagamentoService.pagar(despesaFixaId, auth.getName()));
    }

    @GetMapping("/saldo-previsto")
    public ResponseEntity<BigDecimal> saldoPrevisto(Authentication auth) {
        return ResponseEntity.ok(pagamentoService.calcularSaldoPrevisto(auth.getName()));
    }
}