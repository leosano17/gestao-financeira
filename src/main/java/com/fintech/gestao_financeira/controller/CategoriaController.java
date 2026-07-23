package com.fintech.gestao_financeira.controller;

import com.fintech.gestao_financeira.model.Categoria;
import com.fintech.gestao_financeira.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    public List<Categoria> listarTodas(Authentication auth) {
        return categoriaService.listarPorUsuario(auth.getName());
    }

    @PostMapping
    public ResponseEntity<Categoria> salvar(@RequestBody Categoria categoria, Authentication auth) {
        return ResponseEntity.ok(categoriaService.salvar(categoria, auth.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        categoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}