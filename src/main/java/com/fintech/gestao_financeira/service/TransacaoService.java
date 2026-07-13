package com.fintech.gestao_financeira.service;

import com.fintech.gestao_financeira.model.Transacao;
import com.fintech.gestao_financeira.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;

    public List<Transacao> listarTodas() {
        return transacaoRepository.findAll();
    }

    public Transacao salvar(Transacao transacao) {
        return transacaoRepository.save(transacao);
    }

    public void deletar(Long id) {
        transacaoRepository.deleteById(id);
    }

    public BigDecimal calcularSaldo() {
        List<Transacao> transacoes = transacaoRepository.findAll();
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
}