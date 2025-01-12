package br.com.purchasing.management.service;

import br.com.purchasing.management.dto.response.CompraResponseDto;
import br.com.purchasing.management.entity.Cliente;
import br.com.purchasing.management.entity.Compra;
import br.com.purchasing.management.entity.Produto;
import br.com.purchasing.management.exception.CompraNotFoundException;
import br.com.purchasing.management.mapper.CompraMapper;
import br.com.purchasing.management.repository.CompraRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CompraServiceTest {

    @Mock
    private CompraRepository compraRepository;

    @Mock
    private CompraMapper compraMapper;

    private CompraService compraService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        compraService = new CompraService(compraRepository, compraMapper);
    }

    @Test
    void deveListarComprasOrdenadasPorValorComSucesso() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Compra compra = new Compra(1L, new Cliente(), new Produto(), 2, BigDecimal.valueOf(200));
        Page<Compra> comprasPage = new PageImpl<>(List.of(compra), pageRequest, 1);

        when(compraRepository.findAllByOrderByValorTotalAsc(pageRequest)).thenReturn(comprasPage);
        when(compraMapper.toResponseDto(any())).thenReturn(new CompraResponseDto("Cliente 1", "12345678900", 1L, "Tinto", BigDecimal.valueOf(100.0), 2, BigDecimal.valueOf(200.0)));

        Page<CompraResponseDto> compras = compraService.listarComprasOrdenadasPorValor(0, 10);

        assertEquals(1, compras.get().count());
        assertEquals("Cliente 1", compras.getContent().getFirst().getNomeCliente());
    }

    @Test
    void deveObterMaiorCompraPorAnoComSucesso() {
        Cliente cliente = new Cliente("12345678900", "Cliente 1", List.of(
                new Compra(1L, new Cliente(), new Produto(), 2, BigDecimal.valueOf(200))
        ));
        Produto produto = new Produto(1L,"Tinto", BigDecimal.valueOf(150.0), "2023", 2023);
        Compra compra = new Compra(1L, cliente, produto, 3, BigDecimal.valueOf(450));

        when(compraRepository.findAll()).thenReturn(List.of(compra));
        when(compraMapper.toResponseDto(any())).thenReturn(new CompraResponseDto("Cliente 1", "12345678900", 1L, "Tinto", BigDecimal.valueOf(150.0), 3, BigDecimal.valueOf(450.0)));

        CompraResponseDto maiorCompra = compraService.obterMaiorCompraPorAno(2023);

        assertEquals("Tinto", maiorCompra.getTipoVinho());
        assertEquals(BigDecimal.valueOf(450.0), maiorCompra.getValorTotal());
    }

    @Test
    void deveLancarExcecaoQuandoNaoExistirCompraParaAno() {
        when(compraRepository.findAll()).thenReturn(List.of());

        assertThrows(CompraNotFoundException.class, () -> compraService.obterMaiorCompraPorAno(2023));
    }
}
