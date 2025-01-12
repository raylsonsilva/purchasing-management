package br.com.purchasing.management.service;

import br.com.purchasing.management.dto.ClienteDto;
import br.com.purchasing.management.dto.response.ClienteFielResponseDto;
import br.com.purchasing.management.dto.response.RecomendacaoVinhoResponseDto;
import br.com.purchasing.management.entity.Cliente;
import br.com.purchasing.management.entity.Compra;
import br.com.purchasing.management.exception.ClienteNotFoundException;
import br.com.purchasing.management.exception.JsonClienteProcessingException;
import br.com.purchasing.management.exception.NoPurchasesFoundException;
import br.com.purchasing.management.mapper.ClienteMapper;
import br.com.purchasing.management.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final RestTemplate restTemplate;
    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    @Autowired
    public ClienteService(RestTemplate restTemplate, ClienteRepository clienteRepository,
                          ClienteMapper clienteMapper) {
        this.restTemplate = restTemplate;
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    public void consumirJsonClientes(String url) {
        ResponseEntity<ClienteDto[]> response = restTemplate.getForEntity(url, ClienteDto[].class);

        if (response.getStatusCode() == HttpStatus.OK) {
            ClienteDto[] clienteDtos = response.getBody();

            if (clienteDtos != null) {
                List<Cliente> clientes = processarClientes(clienteDtos);
                clienteRepository.saveAll(clientes);
            } else {
                throw new JsonClienteProcessingException("JSON de clientes retornou nulo.");
            }
        } else {
            throw new JsonClienteProcessingException("Falha ao consumir JSON de clientes: " + response.getStatusCode());
        }
    }

    private List<Cliente> processarClientes(ClienteDto[] clienteDtos) {
        return List.of(clienteDtos).stream()
                .map(clienteMapper::toEntity)
                .collect(Collectors.toList());
    }

    public List<ClienteFielResponseDto> obterClientesFieis() {
        return clienteRepository.findAll().stream()
                .sorted(Comparator.comparing(this::calcularTotalCompras).reversed())
                .limit(3)
                .map(clienteMapper::toFielResponseDto)
                .collect(Collectors.toList());
    }

    private BigDecimal calcularTotalCompras(Cliente cliente) {
        return cliente.getCompras().stream()
                .map(Compra::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public RecomendacaoVinhoResponseDto obterRecomendacaoVinho(String cpf) {
        Cliente cliente = clienteRepository.findById(cpf)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado: " + cpf));

        String tipoMaisComprado = cliente.getCompras().stream()
                .collect(Collectors.groupingBy(compra -> compra.getProduto().getTipoVinho(), Collectors.summingInt(Compra::getQuantidade)))
                .entrySet().stream()
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new NoPurchasesFoundException("Nenhuma compra encontrada para o cliente: " + cpf));

        return new RecomendacaoVinhoResponseDto(cliente.getNome(), cliente.getCpf(), tipoMaisComprado);
    }
}
