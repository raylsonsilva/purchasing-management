package br.com.purchasing.management.service;

import br.com.purchasing.management.dto.ClienteDto;
import br.com.purchasing.management.dto.response.ClienteFielResponseDto;
import br.com.purchasing.management.dto.response.RecomendacaoVinhoResponseDto;
import br.com.purchasing.management.entity.Cliente;
import br.com.purchasing.management.entity.Compra;
import br.com.purchasing.management.entity.Produto;
import br.com.purchasing.management.exception.ClienteNotFoundException;
import br.com.purchasing.management.mapper.ClienteMapper;
import br.com.purchasing.management.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ClienteServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ClienteMapper clienteMapper;

    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clienteService = new ClienteService(restTemplate, clienteRepository, clienteMapper);
    }

    @Test
    void deveConsumirJsonClientesComSucesso() {
        ClienteDto[] clienteDtos = {
                new ClienteDto("Cliente 1", "12345678900", List.of()),
                new ClienteDto("Cliente 2", "98765432100", List.of())
        };

        when(restTemplate.getForEntity(anyString(), eq(ClienteDto[].class)))
                .thenReturn(new ResponseEntity<>(clienteDtos, HttpStatus.OK));

        assertDoesNotThrow(() -> clienteService.consumirJsonClientes("http://mock-url"));

        verify(clienteRepository, times(1)).saveAll(anyList());
    }

    @Test
    void deveLancarExcecaoQuandoClienteNaoEncontrado() {
        when(clienteRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(ClienteNotFoundException.class, () -> clienteService.obterRecomendacaoVinho("12345678900"));
    }

    @Test
    void deveObterClientesFieisComSucesso() {
        Cliente cliente = new Cliente("12345678900", "Cliente 1", List.of(
                new Compra(1L, new Cliente(), new Produto(), 2, BigDecimal.valueOf(200))
        ));

        when(clienteRepository.findAll()).thenReturn(List.of(cliente));
        when(clienteMapper.toFielResponseDto(any())).thenReturn(new ClienteFielResponseDto("Cliente 1", "12345678900", BigDecimal.valueOf(200.0)));

        List<ClienteFielResponseDto> clientesFieis = clienteService.obterClientesFieis();

        assertEquals(1, clientesFieis.size());
        assertEquals("Cliente 1", clientesFieis.get(0).getNome());
    }

    @Test
    void deveObterRecomendacaoDeVinhoComSucesso() {
        Cliente cliente = new Cliente("12345678900", "Cliente 1", List.of(
                new Compra(1L, new Cliente(), new Produto(1L,"Tinto",BigDecimal.TEN,"Safra01",2025), 3, BigDecimal.valueOf(300))
        ));

        when(clienteRepository.findById(anyString())).thenReturn(Optional.of(cliente));

        RecomendacaoVinhoResponseDto recomendacao = clienteService.obterRecomendacaoVinho("12345678900");

        assertEquals("Tinto", recomendacao.getTipoVinhoRecomendado());
    }
}

