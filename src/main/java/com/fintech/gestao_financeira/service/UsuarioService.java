package com.fintech.gestao_financeira.service;

import com.fintech.gestao_financeira.model.Usuario;
import com.fintech.gestao_financeira.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoriaService categoriaService;

    public Usuario cadastrar(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        Usuario salvo = usuarioRepository.save(usuario);
        categoriaService.criarCategoriasPadrao(salvo.getEmail());
        return salvo;
    }
}