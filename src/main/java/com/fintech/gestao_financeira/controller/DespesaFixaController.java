package com.fintech.gestao_financeira.controller;

import com.fintech.gestao_financeira.model.DespesaFixa;
import com.fintech.gestao_financeira.service.DespesaFixaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/despesas-fixas")
@RequiredArgsConstructor
public class DespesaFixaController {

    private final DespesaFixaService despesaFixaService;

    @GetMapping
    public List<DespesaFixa> listar(Authentication auth) {
        return despesaFixaService.listar(auth.getName());
    }

    @PostMapping
    public ResponseEntity<DespesaFixa> salvar(@Valid @RequestBody DespesaFixa despesaFixa, Authentication auth) {
        return ResponseEntity.ok(despesaFixaService.salvar(despesaFixa, auth.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DespesaFixa> editar(@PathVariable Long id, @Valid @RequestBody DespesaFixa despesaFixa, Authentication auth) {
        return ResponseEntity.ok(despesaFixaService.editar(id, despesaFixa, auth.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id, Authentication auth) {
        despesaFixaService.deletar(id, auth.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/total")
    public ResponseEntity<BigDecimal> totalMensal(Authentication auth) {
        return ResponseEntity.ok(despesaFixaService.totalMensal(auth.getName()));
    }
}