package br.com.purchasing.management.service;

import br.com.purchasing.management.dto.ProdutoDto;
import br.com.purchasing.management.entity.Produto;
import br.com.purchasing.management.mapper.ProdutoMapper;
import br.com.purchasing.management.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProdutoServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ProdutoMapper produtoMapper;

    private ProdutoService produtoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        produtoService = new ProdutoService(restTemplate, produtoRepository, produtoMapper);
    }

    @Test
    void deveConsumirJsonProdutosComSucesso() {
        ProdutoDto[] produtoDtos = {
                new ProdutoDto(1L, "Tinto", BigDecimal.valueOf(100.0), "2017", 2018),
                new ProdutoDto(2L, "Branco", BigDecimal.valueOf(200.0), "2018", 2019)
        };

        when(restTemplate.getForEntity(anyString(), eq(ProdutoDto[].class)))
                .thenReturn(new ResponseEntity<>(produtoDtos, HttpStatus.OK));

        when(produtoMapper.toEntity(any())).thenReturn(new Produto());

        assertDoesNotThrow(() -> produtoService.consumirJsonProdutos("http://mock-url"));

        verify(produtoRepository, times(1)).saveAll(anyList());
    }

    @Test
    void deveLancarExcecaoQuandoFalhaAoConsumirJsonProdutos() {
        when(restTemplate.getForEntity(anyString(), eq(ProdutoDto[].class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

        try {
            produtoService.consumirJsonProdutos("http://mock-url");
        } catch (Exception e) {
            assert e.getMessage().contains("Falha ao consumir JSON de produtos");
        }

        verify(produtoRepository, never()).saveAll(anyList());
    }
}

