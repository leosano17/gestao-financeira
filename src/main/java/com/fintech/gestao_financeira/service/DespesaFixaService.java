package com.fintech.gestao_financeira.service;

import com.fintech.gestao_financeira.exception.ResourceNotFoundException;
import com.fintech.gestao_financeira.exception.UnauthorizedException;
import com.fintech.gestao_financeira.model.DespesaFixa;
import com.fintech.gestao_financeira.model.Usuario;
import com.fintech.gestao_financeira.repository.DespesaFixaRepository;
import com.fintech.gestao_financeira.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DespesaFixaService {

    private final DespesaFixaRepository despesaFixaRepository;
    private final UsuarioRepository usuarioRepository;

    public List<DespesaFixa> listar(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        return despesaFixaRepository.findByUsuarioAndAtiva(usuario, true);
    }

    public DespesaFixa salvar(DespesaFixa despesaFixa, String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        despesaFixa.setUsuario(usuario);
        return despesaFixaRepository.save(despesaFixa);
    }

    public DespesaFixa editar(Long id, DespesaFixa dados, String email) {
        DespesaFixa despesa = despesaFixaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Despesa não encontrada"));

        if (!despesa.getUsuario().getEmail().equals(email)) {
            throw new UnauthorizedException("Sem permissão");
        }

        despesa.setDescricao(dados.getDescricao());
        despesa.setValor(dados.getValor());
        despesa.setDiaVencimento(dados.getDiaVencimento());
        despesa.setCategoria(dados.getCategoria());
        despesa.setAtiva(dados.isAtiva());

        return despesaFixaRepository.save(despesa);
    }

    public void deletar(Long id, String email) {
        DespesaFixa despesa = despesaFixaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Despesa não encontrada"));

        if (!despesa.getUsuario().getEmail().equals(email)) {
            throw new UnauthorizedException("Sem permissão");
        }

        despesaFixaRepository.deleteById(id);
    }

    public BigDecimal totalMensal(String email) {
        return listar(email).stream()
                .map(DespesaFixa::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}