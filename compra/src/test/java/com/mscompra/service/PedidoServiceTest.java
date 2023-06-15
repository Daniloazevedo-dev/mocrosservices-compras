package com.mscompra.service;

import com.mscompra.DadosMock;
import com.mscompra.model.Pedido;
import com.mscompra.repository.PedidoRepository;
import com.mscompra.service.exception.NegocioException;
import com.mscompra.service.rabbitmq.Producer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private Producer producer;

    private DadosMock dadosMock = new DadosMock();

    @DisplayName("Salvar pedido com sucesso")
    @Test
    void deveSalvarUmPedidoComSucesso() {
        var pedidoMock = dadosMock.getPedido();

        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoMock);
        doNothing().when(producer).enviarPedido(any(Pedido.class));

        var pedidoSalvo = pedidoService.salvar(pedidoMock);
        assertEquals(pedidoMock.getCep(), pedidoSalvo.getCep());
        assertNotNull(pedidoSalvo.getCep());
    }

    @DisplayName("Deve falhar na busca de pedido que não existe")
    @Test
    void deveFalharNaBuscaDePedidoNaoExistente() {
        var id = 1L;

        Throwable exception = assertThrows(NegocioException.class, () -> {
            Pedido pedidoSalvo = pedidoService.buscarOuFalharPorId(id);
        });

        assertEquals(exception.getMessage(), "O Pedido de id : " + id + "não existe na base da dados");

    }

    @DisplayName("Deve buscar um pedido com sucesso na base de dados")
    @Test
    void deveBuscarUmPedidoComSucesso() {
        var pedidoMock = dadosMock.getPedidoSalvo();
        var id = 1L;

        when(pedidoRepository.findById(any(Long.class))).thenReturn(Optional.of(pedidoMock));

        var pedidoSalvo = pedidoService.buscarOuFalharPorId(id);
        assertEquals(pedidoMock.getId(), pedidoSalvo.getId());
        assertNotNull(pedidoSalvo);
        verify(pedidoRepository, atLeastOnce()).findById(id);
    }

    @DisplayName("Deve excluir um pedido com sucesso")
    @Test
    void deveExcluirPedidoComSucesso() {
        var id = 1L;
        when(pedidoRepository.findById(any(Long.class))).thenReturn(Optional.of(dadosMock.getPedidoSalvo()));
        doNothing().when(pedidoRepository).deleteById(any(Long.class));
        pedidoService.excluir(id);
        verify(pedidoRepository, atLeastOnce()).deleteById(id);
    }

    @DisplayName("Deve falhar ao excluir um pedido que não existe")
    @Test
    void deveFalharAoExcluirUmPedidoNaoExistente() {
        var id = 1L;
        when(pedidoRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Throwable exception = assertThrows(NegocioException.class, () -> pedidoService.excluir(id));

        assertEquals(exception.getMessage(), "O Pedido de id : " + id + "não existe na base da dados");

    }

}
