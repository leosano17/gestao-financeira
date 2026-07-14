package com.fintech.gestao_financeira;

import com.fintech.gestao_financeira.exception.ResourceNotFoundException;
import com.fintech.gestao_financeira.exception.UnauthorizedException;
import com.fintech.gestao_financeira.model.TipoTransacao;
import com.fintech.gestao_financeira.model.Transacao;
import com.fintech.gestao_financeira.model.Usuario;
import com.fintech.gestao_financeira.repository.TransacaoRepository;
import com.fintech.gestao_financeira.repository.UsuarioRepository;
import com.fintech.gestao_financeira.service.TransacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private TransacaoService transacaoService;

    private Usuario usuario;
    private Transacao transacao;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("leo@email.com");
        usuario.setNome("Léo");

        transacao = new Transacao();
        transacao.setId(1L);
        transacao.setDescricao("Salário");
        transacao.setValor(new BigDecimal("5000.00"));
        transacao.setData(LocalDate.now());
        transacao.setTipo(TipoTransacao.ENTRADA);
        transacao.setUsuario(usuario);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontradoAoDeletar() {
        when(transacaoRepository.findById(1L)).thenReturn(Optional.of(transacao));

        assertThrows(UnauthorizedException.class, () ->
                transacaoService.deletar(1L, "outro@email.com")
        );
    }

    @Test
    void deveLancarExcecaoQuandoTransacaoNaoEncontrada() {
        when(transacaoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                transacaoService.deletar(99L, "leo@email.com")
        );
    }

    @Test
    void deveSalvarTransacaoComUsuario() {
        when(usuarioRepository.findByEmail("leo@email.com")).thenReturn(Optional.of(usuario));
        when(transacaoRepository.save(any())).thenReturn(transacao);

        Transacao resultado = transacaoService.salvar(transacao, "leo@email.com");

        assertNotNull(resultado);
        assertEquals(usuario, resultado.getUsuario());
        verify(transacaoRepository, times(1)).save(transacao);
    }
}