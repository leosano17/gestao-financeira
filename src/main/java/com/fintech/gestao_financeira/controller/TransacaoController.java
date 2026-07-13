package com.fintech.gestao_financeira.controller;

import com.fintech.gestao_financeira.model.Transacao;
import com.fintech.gestao_financeira.service.TransacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transacoes")
@RequiredArgsConstructor
public class TransacaoController {

    private final TransacaoService transacaoService;

    @GetMapping
    public List<Transacao> listarTodas(Authentication auth) {
        return transacaoService.listarPorUsuario(auth.getName());
    }

    @PostMapping
    public ResponseEntity<Transacao> salvar(@Valid @RequestBody Transacao transacao,
                                            Authentication auth) {
        return ResponseEntity.ok(transacaoService.salvar(transacao, auth.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        transacaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/saldo")
    public ResponseEntity<BigDecimal> consultarSaldo(Authentication auth) {
        return ResponseEntity.ok(transacaoService.calcularSaldo(auth.getName()));
    }
}