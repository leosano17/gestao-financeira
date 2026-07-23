package com.fintech.gestao_financeira.service;

import com.fintech.gestao_financeira.model.Categoria;
import com.fintech.gestao_financeira.model.Usuario;
import com.fintech.gestao_financeira.repository.CategoriaRepository;
import com.fintech.gestao_financeira.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;

    public List<Categoria> listarPorUsuario(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return categoriaRepository.findByUsuario(usuario);
    }

    public Categoria salvar(Categoria categoria, String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        categoria.setUsuario(usuario);
        return categoriaRepository.save(categoria);
    }

    public void deletar(Long id) {
        categoriaRepository.deleteById(id);
    }

    public void criarCategoriasPadrao(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<String> categoriasPadrao = List.of(
                "Alimentação", "Transporte", "Saúde", "Lazer",
                "Moradia", "Salário", "Educação", "Outros"
        );

        for (String nome : categoriasPadrao) {
            if (!categoriaRepository.existsByNomeAndUsuario(nome, usuario)) {
                Categoria categoria = new Categoria();
                categoria.setNome(nome);
                categoria.setUsuario(usuario);
                categoriaRepository.save(categoria);
            }
        }
    }
}