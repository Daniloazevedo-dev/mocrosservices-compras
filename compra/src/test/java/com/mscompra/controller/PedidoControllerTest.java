package com.mscompra.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mscompra.CompraApplication;
import com.mscompra.DadosMock;
import com.mscompra.model.Pedido;
import com.mscompra.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CompraApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PedidoControllerTest {

    private final MockMvc mockMvc;

    private final PedidoService pedidoService;

    private final DadosMock dadosMock = new DadosMock();

    private final ObjectMapper mapper;

    private static final String ROTA_PEDIDO = "/pedido";

    @DisplayName("POST - Deve cadastrar um novo pedido com sucesso no banco de dados")
    @Test
    void deveCadastrarPedidoComSucesso() throws Exception {
        var pedidoBody = dadosMock.getPedido();
        var id = 1L;

        mockMvc.perform(post(ROTA_PEDIDO)
                .content(mapper.writeValueAsString(pedidoBody))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        Pedido pedidoSalvo  = pedidoService.buscarOuFalharPorId(id);
        assertEquals(pedidoSalvo.getId(), id);
        assertNotNull(pedidoSalvo);

    }

}
