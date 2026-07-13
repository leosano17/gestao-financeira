package com.fintech.gestao_financeira.controller;

import com.fintech.gestao_financeira.model.LoginRequest;
import com.fintech.gestao_financeira.model.Usuario;
import com.fintech.gestao_financeira.repository.UsuarioRepository;
import com.fintech.gestao_financeira.service.JwtService;
import com.fintech.gestao_financeira.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/cadastro")
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.cadastrar(usuario));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        return usuarioRepository.findByEmail(request.getEmail())
                .filter(u -> passwordEncoder.matches(request.getSenha(), u.getSenha()))
                .map(u -> ResponseEntity.ok(jwtService.gerarToken(u.getEmail())))
                .orElse(ResponseEntity.status(401).build());
    }
}