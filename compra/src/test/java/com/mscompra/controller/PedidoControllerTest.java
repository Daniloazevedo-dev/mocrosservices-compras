package com.mscompra.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mscompra.CompraApplication;
import com.mscompra.DadosMock;
import com.mscompra.model.Pedido;
import com.mscompra.service.PedidoService;
import com.mscompra.service.exception.EntidadeNaoEncontradaException;
import com.mscompra.service.exception.NegocioException;
import com.mscompra.service.rabbitmq.Producer;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CompraApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private Producer producer;

    private static final String ROTA_PEDIDO = "/pedido";

    private DadosMock dadosMok = new DadosMock();

    @DisplayName("POST - Deve cadastrar um novo pedido com sucesso no banco de dados")
    @Test
    void testA() throws Exception {
        var pedidoBody = dadosMok.getPedido();
        var id = 1L;

        doNothing().when(producer).enviarPedido(pedidoBody);

        mockMvc.perform(post(ROTA_PEDIDO)
                        .content(mapper.writeValueAsString(pedidoBody))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        Pedido pedidoSalvo = pedidoService.buscarOuFalharPorId(id);

        assertEquals(pedidoSalvo.getId(), id);
        assertNotNull(pedidoSalvo);
    }

    @DisplayName("GET - Deve buscar o pedido com sucesso na base de dados")
    @Test
    void testB() throws Exception {
        var id = 1L;

        mockMvc.perform(get(ROTA_PEDIDO.concat("/" + id)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("DELETE - Deve excluir um pedido com sucesso")
    @Test
    void testC() throws Exception {
        var id = 1L;

        mockMvc.perform(delete(ROTA_PEDIDO.concat("/" + id)))
                .andDo(print())
                .andExpect(status().isNoContent());

        Throwable exception = assertThrows(EntidadeNaoEncontradaException.class, () -> pedidoService.buscarOuFalharPorId(id));

        assertEquals(exception.getMessage(), "O Pedido de id : " + id + " não existe na base da dados");

    }

    @DisplayName("GET - Deve falhar ao buscar pedido que não existe")
    @Test
    void testD() throws Exception {
        var id = 2L;

        mockMvc.perform(get(ROTA_PEDIDO.concat("/" + id)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
