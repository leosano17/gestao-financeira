package com.fintech.gestao_financeira.service;

import com.fintech.gestao_financeira.exception.ResourceNotFoundException;
import com.fintech.gestao_financeira.exception.UnauthorizedException;
import com.fintech.gestao_financeira.model.Transacao;
import com.fintech.gestao_financeira.model.Usuario;
import com.fintech.gestao_financeira.repository.TransacaoRepository;
import com.fintech.gestao_financeira.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.fintech.gestao_financeira.model.TipoTransacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final UsuarioRepository usuarioRepository;

    public Page<Transacao> listarPorUsuario(String email, Pageable pageable) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        return transacaoRepository.findByUsuario(usuario, pageable);
    }

    public Transacao salvar(Transacao transacao, String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        transacao.setUsuario(usuario);
        return transacaoRepository.save(transacao);
    }

    public void deletar(Long id, String email) {
        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transação não encontrada"));

        if (transacao.getUsuario() == null ||
                !transacao.getUsuario().getEmail().equals(email)) {
            throw new UnauthorizedException("Você não tem permissão para deletar essa transação");
        }

        transacaoRepository.deleteById(id);
    }

    public BigDecimal calcularSaldo(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        List<Transacao> transacoes = transacaoRepository.findByUsuario(usuario, Pageable.unpaged()).getContent();
        BigDecimal saldo = BigDecimal.ZERO;

        for (Transacao t : transacoes) {
            if (t.getTipo().name().equals("ENTRADA")) {
                saldo = saldo.add(t.getValor());
            } else {
                saldo = saldo.subtract(t.getValor());
            }
        }
        return saldo;

    }
    public Page<Transacao> filtrar(String email, LocalDate dataInicio, LocalDate dataFim,
                                   TipoTransacao tipo, Long categoriaId, Pageable pageable) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        return transacaoRepository.filtrar(usuario, dataInicio, dataFim, tipo, categoriaId, pageable);
    }
}