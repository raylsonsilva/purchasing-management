package br.com.purchasing.management.controller;

import br.com.purchasing.management.dto.response.ClienteFielResponseDto;
import br.com.purchasing.management.dto.response.RecomendacaoVinhoResponseDto;
import br.com.purchasing.management.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Test
    void deveImportarClientesComSucesso() throws Exception {
        doNothing().when(clienteService).consumirJsonClientes(anyString());

        mockMvc.perform(post("/api/v1/clientes/importar")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(clienteService, times(1)).consumirJsonClientes(anyString());
    }

    @Test
    void deveObterClientesFieisComSucesso() throws Exception {
        List<ClienteFielResponseDto> clientesFieis = List.of(
                new ClienteFielResponseDto("Cliente 1", "12345678900", BigDecimal.valueOf(1000.0)),
                new ClienteFielResponseDto("Cliente 2", "98765432100", BigDecimal.valueOf(800.0))
        );

        when(clienteService.obterClientesFieis()).thenReturn(clientesFieis);

        mockMvc.perform(get("/api/v1/clientes/fieis")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(clienteService, times(1)).obterClientesFieis();
    }

    @Test
    void deveObterRecomendacaoDeVinhoComSucesso() throws Exception {
        RecomendacaoVinhoResponseDto recomendacao = new RecomendacaoVinhoResponseDto("Cliente 1", "12345678900", "Tinto");

        when(clienteService.obterRecomendacaoVinho(anyString())).thenReturn(recomendacao);

        mockMvc.perform(get("/api/v1/clientes/recomendacao/12345678900/tipo")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(clienteService, times(1)).obterRecomendacaoVinho(anyString());
    }
}
