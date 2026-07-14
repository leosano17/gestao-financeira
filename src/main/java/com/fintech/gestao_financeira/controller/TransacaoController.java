package com.fintech.gestao_financeira.controller;

import com.fintech.gestao_financeira.model.Transacao;
import com.fintech.gestao_financeira.service.TransacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.fintech.gestao_financeira.model.TipoTransacao;
import java.time.LocalDate;

import java.math.BigDecimal;

@RestController
@RequestMapping("/transacoes")
@RequiredArgsConstructor
public class TransacaoController {

    private final TransacaoService transacaoService;

    @GetMapping
    public Page<Transacao> listarTodas(
            Authentication auth,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanho) {

        Pageable pageable = PageRequest.of(pagina, tamanho, Sort.by("data").descending());
        return transacaoService.listarPorUsuario(auth.getName(), pageable);
    }

    @PostMapping
    public ResponseEntity<Transacao> salvar(@Valid @RequestBody Transacao transacao,
                                            Authentication auth) {
        return ResponseEntity.ok(transacaoService.salvar(transacao, auth.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id, Authentication auth) {
        transacaoService.deletar(id, auth.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/saldo")
    public ResponseEntity<BigDecimal> consultarSaldo(Authentication auth) {
        return ResponseEntity.ok(transacaoService.calcularSaldo(auth.getName()));
    }
    @GetMapping("/filtrar")
    public Page<Transacao> filtrar(
            Authentication auth,
            @RequestParam(required = false) LocalDate dataInicio,
            @RequestParam(required = false) LocalDate dataFim,
            @RequestParam(required = false) TipoTransacao tipo,
            @RequestParam(required = false) Long categoriaId,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamanho) {

        Pageable pageable = PageRequest.of(pagina, tamanho, Sort.by("data").descending());
        return transacaoService.filtrar(auth.getName(), dataInicio, dataFim, tipo, categoriaId, pageable);
    }
}