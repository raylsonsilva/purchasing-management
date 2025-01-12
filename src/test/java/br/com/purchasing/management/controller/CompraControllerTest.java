package br.com.purchasing.management.controller;

import br.com.purchasing.management.dto.response.CompraResponseDto;
import br.com.purchasing.management.service.CompraService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CompraController.class)
public class CompraControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompraService compraService;

    @Test
    void deveListarComprasOrdenadasPorValorComSucesso() throws Exception {
        Page<CompraResponseDto> compras = new PageImpl<>(List.of(
                new CompraResponseDto("Cliente 1", "12345678900", 1L, "Tinto", BigDecimal.valueOf(100.0), 2, BigDecimal.valueOf(200.0)),
                new CompraResponseDto("Cliente 2", "98765432100", 2L, "Branco", BigDecimal.valueOf(50.0), 4, BigDecimal.valueOf(200.0))
        ));

        when(compraService.listarComprasOrdenadasPorValor(0, 10)).thenReturn(compras);

        mockMvc.perform(get("/api/v1/compras?page=0&size=10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(compraService, times(1)).listarComprasOrdenadasPorValor(0, 10);
    }

    @Test
    void deveObterMaiorCompraPorAnoComSucesso() throws Exception {
        CompraResponseDto maiorCompra = new CompraResponseDto("Cliente 1", "12345678900", 1L, "Tinto", BigDecimal.valueOf(150.0), 3, BigDecimal.valueOf(450.0));

        when(compraService.obterMaiorCompraPorAno(2023)).thenReturn(maiorCompra);

        mockMvc.perform(get("/api/v1/compras/maior-compra/2023")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(compraService, times(1)).obterMaiorCompraPorAno(2023);
    }
}
