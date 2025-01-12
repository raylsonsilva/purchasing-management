package br.com.purchasing.management.service;

import br.com.purchasing.management.dto.ProdutoDto;
import br.com.purchasing.management.entity.Produto;
import br.com.purchasing.management.exception.JsonProdutoProcessingException;
import br.com.purchasing.management.mapper.ProdutoMapper;
import br.com.purchasing.management.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    private final RestTemplate restTemplate;
    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    @Autowired
    public ProdutoService(RestTemplate restTemplate, ProdutoRepository produtoRepository, ProdutoMapper produtoMapper) {
        this.restTemplate = restTemplate;
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
    }

    public void consumirJsonProdutos(String url) {
        ResponseEntity<ProdutoDto[]> response = restTemplate.getForEntity(url, ProdutoDto[].class);

        if (response.getStatusCode() == HttpStatus.OK) {
            ProdutoDto[] produtoDtos = response.getBody();

            if (produtoDtos != null) {
                List<Produto> produtos = Arrays.stream(produtoDtos)
                        .map(produtoMapper::toEntity)
                        .collect(Collectors.toList());

                produtoRepository.saveAll(produtos);
            } else {
                throw new JsonProdutoProcessingException("JSON de produtos retornou nulo.");
            }
        } else {
            throw new JsonProdutoProcessingException("Falha ao consumir JSON de produtos: " + response.getStatusCode());
        }
    }
}
