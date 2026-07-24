package com.fintech.gestao_financeira.service;

import com.fintech.gestao_financeira.model.DespesaFixa;
import com.fintech.gestao_financeira.model.PagamentoDespesaFixa;
import com.fintech.gestao_financeira.model.Transacao;
import com.fintech.gestao_financeira.model.TipoTransacao;
import com.fintech.gestao_financeira.model.Usuario;
import com.fintech.gestao_financeira.repository.DespesaFixaRepository;
import com.fintech.gestao_financeira.repository.PagamentoDespesaFixaRepository;
import com.fintech.gestao_financeira.repository.TransacaoRepository;
import com.fintech.gestao_financeira.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagamentoDespesaFixaService {

    private final PagamentoDespesaFixaRepository pagamentoRepository;
    private final DespesaFixaRepository despesaFixaRepository;
    private final TransacaoRepository transacaoRepository;
    private final UsuarioRepository usuarioRepository;

    public List<PagamentoDespesaFixa> listarDoMes(String email, Integer mes, Integer ano) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        List<DespesaFixa> despesas = despesaFixaRepository.findByUsuarioAndAtiva(usuario, true);
        return pagamentoRepository.findByDespesaFixaInAndMesAndAno(despesas, mes, ano);
    }

    public PagamentoDespesaFixa pagar(Long despesaFixaId, String email) {
        DespesaFixa despesa = despesaFixaRepository.findById(despesaFixaId)
                .orElseThrow(() -> new RuntimeException("Despesa não encontrada"));

        LocalDate hoje = LocalDate.now();
        Integer mes = hoje.getMonthValue();
        Integer ano = hoje.getYear();

        PagamentoDespesaFixa pagamento = pagamentoRepository
                .findByDespesaFixaAndMesAndAno(despesa, mes, ano)
                .orElse(new PagamentoDespesaFixa());

        pagamento.setDespesaFixa(despesa);
        pagamento.setMes(mes);
        pagamento.setAno(ano);
        pagamento.setDataPagamento(hoje);
        pagamento.setPago(true);

        pagamentoRepository.save(pagamento);

        Transacao transacao = new Transacao();
        transacao.setDescricao("(Fixo) " + despesa.getDescricao());
        transacao.setValor(despesa.getValor());
        transacao.setData(hoje);
        transacao.setTipo(TipoTransacao.SAIDA);
        transacao.setCategoria(despesa.getCategoria());
        transacao.setUsuario(despesa.getUsuario());
        transacaoRepository.save(transacao);

        return pagamento;
    }

    public BigDecimal calcularSaldoPrevisto(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<DespesaFixa> despesas = despesaFixaRepository.findByUsuarioAndAtiva(usuario, true);

        LocalDate hoje = LocalDate.now();
        Integer mes = hoje.getMonthValue();
        Integer ano = hoje.getYear();

        List<PagamentoDespesaFixa> pagamentos = pagamentoRepository
                .findByDespesaFixaInAndMesAndAno(despesas, mes, ano);

        BigDecimal totalNaoPago = despesas.stream()
                .filter(d -> pagamentos.stream()
                        .noneMatch(p -> p.getDespesaFixa().getId().equals(d.getId()) && p.isPago()))
                .map(DespesaFixa::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalNaoPago;
    }
}